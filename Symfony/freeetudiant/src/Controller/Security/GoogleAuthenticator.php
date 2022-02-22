<?php


namespace App\Controller\Security;

use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;

use KnpU\OAuth2ClientBundle\Client\ClientRegistry;
use KnpU\OAuth2ClientBundle\Security\Authenticator\SocialAuthenticator;
use League\OAuth2\Client\Provider\GoogleUser;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\RouterInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Core\Exception\AuthenticationException;
use Symfony\Component\Security\Core\User\UserProviderInterface;

class GoogleAuthenticator extends SocialAuthenticator
{
    private $clientRegistry;
    private $em;
    private $router;

    public function __construct(ClientRegistry $clientRegistry, EntityManagerInterface $em, RouterInterface $router)
    {
        $this->clientRegistry = $clientRegistry;
        $this->em = $em;
        $this->router = $router;
    }

    public function supports(Request $request)
    { return $request->getPathInfo()=='/connect/google/check'&&$request->isMethod('GET');
        // continue ONLY if the current ROUTE matches the check ROUTE
        //
    }

    public function getCredentials(Request $request)
    {
        // this method is only called if supports() returns true

        // For Symfony lower than 3.4 the supports method need to be called manually here:
        // if (!$this->supports($request)) {
        //     return null;
        // }

        return $this->fetchAccessToken($this->getGoogleClient());
    }

    public function getUser($credentials, UserProviderInterface $userProvider)
    {      $user = new User();
        /** @var GoogleUser $googleUser */
        $googleUser = $this->getGoogleClient()
            ->fetchUserFromToken($credentials);

        $email = $googleUser->getEmail();

        $existingUser = $this->em->getRepository(User::class)
            ->findOneBy(['email' => $email]);
        if ($existingUser) {
            return $existingUser;
        }

        // 2) do we have a matching user by email?
        $user = $this->em->getRepository(User::class)
            ->findOneBy(['email' => $email]);

        // 3) Maybe you just want to "register" them by creating
        // a User object
        $user =new User();
        $user->setId($googleUser->getId());
        $user->setNom($googleUser->getLastName()." ".$googleUser->getFirstName());
        $user->setPhoto($googleUser->getAvatar());
        $user->setEmail($googleUser->getEmail());
        $user->setPassword("123456aA");
        $user->setRoles(array('ROLE_ETUDIANT'));
        $user->setTel("NULL");
        $this->em->persist($user);
        $this->em->flush();

        return $user;
    }

    /**

     */
    private function getGoogleClient()
    {
        //* @return \KnpU\OAuth2ClientBundle\Client\OAuth2Client
        return $this->clientRegistry
            ->getClient('google');
    }

    public function onAuthenticationSuccess(Request $request, TokenInterface $token, $providerKey)
    {
        // change "app_homepage" to some route in your app
        // $targetUrl = $this->router->generate('app_homepage');

        return new RedirectResponse('/home');

        // or, on success, let the request continue to be handled by the controller
        //return null;
    }

    public function onAuthenticationFailure(Request $request, AuthenticationException $exception)
    {
        //    $message = strtr($exception->getMessageKey(), $exception->getMessageData());

        //  return new Response($message, Response::HTTP_FORBIDDEN);
    }

    /**
     * Called when authentication is needed, but it's not sent.
     * This redirects to the 'login'.
     */
    public function start(Request $request, AuthenticationException $authException = null)
    {
        return new RedirectResponse('/login');

    }

    // ...
}