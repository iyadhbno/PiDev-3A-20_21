<?php

namespace App\Controller;

use App\Form\EntrepriseModifyType;
use App\Services\GetUser;
use App\Entity\User;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


class ChangeInformationsEntrepriseController extends AbstractController
{
    private $user;


    public function __construct(GetUser $Get_User)
    {
        $this->user = $Get_User->Get_User();

        if($Get_User == null)
        {
            return $this->redirectToRoute('login');
        }
    }


    /**
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/Entreprise/Profil/Change_informations", name="ProfilEntreprise")
     */


    function modifier(Request $request)
    {   $entityManager = $this->getDoctrine()->getManager();
        $form = $this->createForm(EntrepriseModifyType::class,$this->user);
        $form->handleRequest($request);
        $file = $form->get('photo')->getData();
        if($form->isSubmitted()&&$form->isValid())
        {
            if($file)
            {
                $fileName = md5(uniqid()).'.'.$file->guessExtension();
                $file->move($this->getParameter('images_directory'), $fileName);
                $this->user->setPhoto($fileName);
                $form->getData()->setPhoto($fileName);
            }
            $entityManager->flush();
            $this->addFlash('info','Vos informations sont a jour');
        }
        return $this->render('entreprise/profil.html.twig',
            ['form_modify' => $form->createView(),
                'User'=>$this->user,
            ]);

    }






}

