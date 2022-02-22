<?php

namespace App\Controller;

use App\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class Statistiques extends AbstractController
{
    /**
     * @Route("/admin/statistiques", name="statistiques")
     */
    public function index(): Response
    {



        $Entreprises=$this->getDoctrine()->getRepository(User::class)->findBy(['roles' => array('["ROLE_ENTREPRISE"]')]);
        $EntreprisesNbr=sizeof($Entreprises);


        $Etudiants=$this->getDoctrine()->getRepository(User::class)->findBy(['roles' => array('["ROLE_ETUDIANT"]')]);
        $EtudiantsNbr=sizeof($Etudiants);




        return $this->render('back/statistiques.html.twig', [
            'controller_name' => 'FrontController',
            'EntreprisesNbr'=>$EntreprisesNbr,'EtudiantsNbr'=>$EtudiantsNbr
        ]);
    }
}
