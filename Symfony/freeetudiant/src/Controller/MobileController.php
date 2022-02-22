<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\EntrepriseModifyType;
use App\Form\EntrepriseType;
use App\Repository\UserRepository;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Filesystem\Filesystem;


class MobileController extends AbstractController
{
    private $user;


    /**
     * @Route("/checkUserUnique", name="checkUserUnique")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function checkUserUnique(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {

        $userBD_email = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get($request->get('email'))]
        );
        if($userBD_email)
        {
            $result=-2;
        }
        else{
            $result=1;
        }
        $jsonContent=$normalizable->normalize($result,'json',[]);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/loginCheck", name="loginCheck")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserRepository $userRepository
     * @return Response
     * @throws ExceptionInterface
     */

    public function loginCheck(Request $request,NormalizerInterface $normalizable,UserRepository $userRepository,UserPasswordEncoderInterface $encoder): Response //mail
    {  $result=-1;
        $user=new User();
        $User = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);

        $password=$encoder->encodePassword($user,$request->get('password'));
        if($User)
        {
            if($User[0]->getPassword()==$password)
            {
                if (in_array('ROLE_ETUDIANT',$User[0]->getRoles(), true))   
                $result=1;
                else if (in_array('ROLE_ENTREPRISE', $User[0]->getRoles(), true))
                    $result=2;
            }}

        $jsonContent=$normalizable->normalize($result,'json',[]);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/getUser", name="getUser")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserRepository $userRepository
     * @return Response
     * @throws ExceptionInterface
     */

    public function getUserByEmail(Request $request,NormalizerInterface $normalizable,UserRepository $userRepository): Response //naateha mail trajaa user
    {
        $user=new User();
        $userEmail = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]
        );

        if($userEmail)
        {
            $user=$userEmail;
        }

        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/modifyEnt", name="modifyEnt")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function modifyEnt(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {
        $em=$this->getDoctrine()->getManager();
        $User = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);
        $user = $User[0];
        $user->setNom($request->get('nom'));
        $user->setTel($request->get('tel'));
        $user->setOffre($request->get('offre'));

       /* if($user->getPhoto()!=$request->get('photo'))
        {
            $fileNamePhoto = $request->get('photo');
            $filePathMobilePhoto="C://Users//ibeno//AppData//Local//Temp";
            $uploads_directoryPic = $this->getParameter('images_directory');
            $filesystempic = new Filesystem();
            $filesystempic->copy($filePathMobilePhoto."//".$fileNamePhoto,$uploads_directoryPic."/$fileNamePhoto");
            $user->setPhoto($request->get('photo'));
        }*/

        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }



 /**
     * @Route("/modifyEtu", name="modifyEtu")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function modifyEtu(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {
        $em=$this->getDoctrine()->getManager();
        $User = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);
        $user = $User[0];
        $user->setNom($request->get('nom'));
        $user->setTel($request->get('tel'));

        /*if($user->getPhoto()!=$request->get('photo'))
        {
            $fileNamePhoto = $request->get('photo');
            $filePathMobilePhoto="C://Users//ibeno//AppData//Local//Temp";
            $uploads_directoryPic = $this->getParameter('images_directory');
            $filesystempic = new Filesystem();
            $filesystempic->copy($filePathMobilePhoto."//".$fileNamePhoto , $uploads_directoryPic."/$fileNamePhoto");
            $user->setPhoto($request->get('photo'));
        }*/

        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }


    

    /**
     * @Route("/newEtudiantM", name="etudiantNewM", methods={"GET","POST"})
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param $encoder
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     */
    public function newEtu(Request $request, NormalizerInterface $normalizable, UserPasswordEncoderInterface $encoder): Response
    {
        $em=$this->getDoctrine()->getManager();
        $user=new User();
        $password=$encoder->encodePassword($user,$request->get('password'));

        $fileNamePhoto = $request->get('photo');
        $filePathMobilePhoto="C://Users//ibeno//AppData//Local//Temp";
        $uploads_directoryPic = $this->getParameter('images_directory');
        $filesystempic = new Filesystem();
        $filesystempic->copy($filePathMobilePhoto."//".$fileNamePhoto , $uploads_directoryPic."/$fileNamePhoto");

        /*$fileNameCv = $request->get('cv');
        $filePathMobileCv="C://Users//ibno//AppData//Local//Temp";
        $uploads_directoryCv = $this->getParameter('cv_directory');
        $filesystemCv = new Filesystem();
        $filesystemCv->copy($filePathMobileCv."//".$fileNameCv,$uploads_directoryCv."/$fileNameCv");*/

        $user->constructEtu($request->get('nom'),$request->get('email'),$password,$request->get('tel'),$request->get('photo'),$request->get('cv'),array('ROLE_ETUDIANT'));
        $em->persist($user);
        $em->flush();

        $jsonContent=$normalizable->normalize($user,'json',[]);
        return new Response(json_encode($jsonContent));
    }



    /**
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/Etudiant/Profil/Change_informationsM", name="ProfilEtudiantM")
     */

    function modifierEtu(Request $request, NormalizerInterface $normalizable): Response
    {
        $em=$this->getDoctrine()->getManager();
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));
        $user->setNom($request->get('nom'));
        $user->setPrenom($request->get('prenom'));
        $user->setTel($request->get('tel'));

        if($user->getPicture()!=$request->get('picture'))
        {
            $fileName = $request->get('picture');
            $filePathMobile="C://Users//SeifBS//AppData//Local//Temp";
            $uploads_directory = $this->getParameter('pictures_directory');
            $filesystem = new Filesystem();
            $filesystem->copy($filePathMobile."//".$fileName,$uploads_directory."/$fileName");
            $user->setPicture($request->get('picture'));
        }

        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));

    }




    /**
     * @Route("/modifyPasswordEtu", name="etudiantModifyPassword")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function modifyEtuPassword(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {   $User=new User();
        $em=$this->getDoctrine()->getManager();
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('email'));
        $password=$encoder->encodePassword($User,$request->get('password'));
        $user->setPassword($password);
        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/modifyPasswordUser", name="UserModifyPassword")
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function modifyEntPassword(Request $request,NormalizerInterface $normalizable,UserPasswordEncoderInterface $encoder): Response
    {   $User=new User();
        $em=$this->getDoctrine()->getManager();
        $user = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);

        $password=$encoder->encodePassword($User,$request->get('password'));

        $user[0]->setPassword($password);
        $em->flush();
        $jsonContent=$normalizable->normalize($user,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }


    /**
     * @Route("/api/client/forgetPasswordCheck", name="api_forgetPasswordCheck")
     * @param MailerService $mailerService
     * @param Request $request
     * @param NormalizerInterface $normalizable
     * @param UserRepository $userRepository
     * @return Response
     * @throws ExceptionInterface
     * @throws \Symfony\Component\Mailer\Exception\TransportExceptionInterface
     * @throws \Twig\Error\LoaderError
     * @throws \Twig\Error\RuntimeError
     * @throws \Twig\Error\SyntaxError
     */

    public function forgetPasswordCheck(Request $request,NormalizerInterface $normalizable,UserRepository $userRepository): Response
    {   $result=false;
        $user=new User();
        $user = $this->getDoctrine()->getRepository(User::class)->findBy(
            ['email' =>$request->get('email')]);

        if($user)
        {
        if($user[0]->getEmail()==$request->get("email"))
        {
            $result=true;

        }}

        $jsonContent=$normalizable->normalize($result,'json',[]);
        return new Response(json_encode($jsonContent));
    }

}