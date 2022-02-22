<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\EtudiantPasswordType;
use App\Services\GetUser;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

class ChangePasswordEntrepriseController extends AbstractController
{

    private $encoder;
    private $user;


    public function __construct(UserPasswordEncoderInterface $encoder, GetUser $Get_User)
    {
        $this->encoder = $encoder;
        $this->user = $Get_User->Get_User();

        if($Get_User == null)
        {
            return $this->redirectToRoute('loginBack');
        }
    }



    /**
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/Entreprise/Profil/Change_Password", name="change_passwordEntreprise")
     */


    function modifier(Request $request)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $form = $this->createForm(EtudiantPasswordType::class,$this->user);
        $form->handleRequest($request);


        if($form->isSubmitted() && $form->isValid())
        {
            $password = $form->getData()->getPassword();
            $form->getData()->setPassword($this->encoder->encodePassword($this->user, $password));
            $entityManager->flush();
            return $this->redirectToRoute('ProfilEntreprise');
        }

        return $this->render('entreprise/change_password/changePassword.html.twig',
            ['form_modify' => $form->createView(),
                'User'=>$this->user,
            ]);

    }
}
