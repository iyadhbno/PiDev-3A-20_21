<?php

namespace App\Controller;

use App\Entity\Role;
use App\Entity\User;
use App\Form\EntrepriseType;
use App\Repository\UserRepository;
use App\Services\MailerService;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

class EntrepriseController extends AbstractController
{
 

    /**
     * @Route("/newEntreprise", name="newEntreprise", methods={"GET","POST"})
     */
    public function new(\Swift_Mailer $mailer,Request $request,MailerService $mailerService,UserPasswordEncoderInterface $encoder): Response
    {
        $user = new User();
        $role = new Role();
        $form = $this->createForm(EntrepriseType::class, $user);
        $user->setRoles(array('ROLE_ENTREPRISEN'));


        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();

            $token=md5(uniqid());
            $user->setActivationToken($token);

            $password = $form->getData()->getPassword();
            $form->getData()->setPassword($encoder->encodePassword($user, $password));


            $filePhoto = $form->get('photo')->getData();


            if($filePhoto)
            {
                $fileNamePhoto = md5(uniqid()).'.'.$filePhoto->guessExtension();
                $filePhoto->move($this->getParameter('images_directory'), $fileNamePhoto);
                $user->setPhoto($fileNamePhoto);
            }


            $entityManager->persist($user);
            $entityManager->flush();
            $mailerService->send(
                "Bienvenue sur notre site",
                "freeetudiant120@gmail.com",
                $form->get('email')->getData(),
                "emailEntreprise.html.twig",['token'=>$token]);



            return $this->redirectToRoute('loginBack', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('entreprise/new.html.twig', [
            'Entreprise' => $user,
            'form' => $form->createView(),
        ]);
    }




    /**
     * @Route("/activation/{token}",name="activation")
     */
    public function activation($token,UserRepository $userRepository)
    {
        $user = new User();
        $user = $userRepository->findOneBy(['activation_token' => $token]);
        if (!$user) {


            return $this->redirectToRoute("loginBack");
        }

        $user->setActivationToken(null);
        $user->setRoles(array('ROLE_ENTREPRISE'));

        $em = $this->getDoctrine()->getManager();
        $em->flush();
        $this->addFlash("info", "Votre Compte est active");


        return $this->redirectToRoute("loginBack");

    }

    /**
     * @Route("/pdfEntreprises",name="pdfEntreprises")
     */

    public function pdf()
    {
        $User=$this->getDoctrine()->getRepository(User::class)->findBy(['roles' => array('["ROLE_ENTREPRISE"]')]);


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        // Retrieve the HTML generated in our twig file
        $date=date("Y/m/d");
        $html = $this->renderView("back/pdfEntreprises.html.twig",[
            'users' => $User,
            'date'=>$date
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);


        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();


        // Output the generated PDF to Browser (force download)
        $dompdf->stream("ListeEntreprises.pdf", [
            "Attachment" => true
        ]);
    }


}
