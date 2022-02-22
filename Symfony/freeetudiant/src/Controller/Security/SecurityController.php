<?php

namespace App\Controller\Security;

use App\Entity\User;
use App\Services\GetUser;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\RouterInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

class SecurityController extends AbstractController
{

    private $user;



    /**
     * @Route("/login", name="loginBack")
     */
    public function login(Request $request,AuthenticationUtils $utils): Response
    {
        $this->user=new User();
        $error=$utils->getLastAuthenticationError();
        $last_id=$utils->getLastUsername();


    return $this->render('security/login1.html.twig', [
            'error' => $error,
            'last_username'=> $last_id

        ]);
        //return AfterLoginRedirection::onAuthenticationSuccess($request,$token);


    }

    /**
     * @Route("/logout", name="logout")
     */
    public function logout(){




    }
}
