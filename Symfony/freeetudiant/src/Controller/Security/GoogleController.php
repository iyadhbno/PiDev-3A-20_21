<?php


namespace App\Controller\Security;


use KnpU\OAuth2ClientBundle\Client\ClientRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

class GoogleController extends AbstractController
{
    /**
     * Link to this controller to start the "connect" process
     *
     * @Route("/connect/google", name="connect_google")
     */
    public function connectAction(ClientRegistry $clientRegistry)
    {

        return $clientRegistry
            ->getClient('google') // key used in config/packages/knpu_oauth2_client.yaml
            ->redirect();
    }

    /**
     * because this is the "redirect_route" you configured
     * in config/packages/knpu_oauth2_client.yaml
     *
     * @Route("/connect/google/check", name="connect_google_check")
     * @param Request $request
     * return JsonResponse \Symfony\Component\HttpFoundation\RedirectResponse
     */
    public function connectCheckAction(Request $request)
    {
        if(!$this->getUser())
        {
            return new JsonResponse(array('status'=>false,'message'=>"User not found"));
        }
        else {
            return $this->redirectToRoute('home');
        }
    }
}