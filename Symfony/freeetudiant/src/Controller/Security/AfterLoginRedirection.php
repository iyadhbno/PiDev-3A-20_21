<?php

namespace App\Controller\Security;


use App\Entity\User;
use App\Services\GetUser;
use App\Services\UpdateRoles;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\RouterInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationSuccessHandlerInterface;

class AfterLoginRedirection extends AbstractController implements AuthenticationSuccessHandlerInterface
{
    /**
     * @var \Symfony\Component\Routing\RouterInterface
     */
    private $router;
    private $getUser;

    /**
     * @param RouterInterface $router
     * @param GetUser $getUser
     */
    public function __construct(RouterInterface $router,GetUser $getUser)
    {
        $this->router = $router;
        $this->getUser=$getUser;

    }



    /**
     * @param Request $request
     * @param TokenInterface $token
     * @return RedirectResponse
     */


    public function onAuthenticationSuccess(Request $request, TokenInterface $token)
    {
         // Get list of roles for current user
        $roles = $token->getRoles();


        // Tranform this list in array
        $rolesTab = array_map(function($role){
            return $role->getRole();
        }, $roles);

        if (in_array('ROLE_ETUDIANT', $rolesTab, true) ||in_array('ROLE_ETUDIANTN', $rolesTab, true))
        {

           $redirection = new RedirectResponse($this->router->generate('home'));
        }

        elseif (in_array('ROLE_ENTREPRISE', $rolesTab, true) ||in_array('ROLE_ENTREPRISEN', $rolesTab, true))
            $redirection = new RedirectResponse($this->router->generate('home'));
        elseif (in_array('ROLE_ADMIN', $rolesTab, true) ||in_array('ROLE_SUPERADMIN', $rolesTab, true) )
            $redirection = new RedirectResponse($this->router->generate('ProfilAdmin'));
        // otherwise we redirect user to the member area
        
        else
            $redirection = new RedirectResponse($this->router->generate('loginBack'));

        return $redirection;
    }
}