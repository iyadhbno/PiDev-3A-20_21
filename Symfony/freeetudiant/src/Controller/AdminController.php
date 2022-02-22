<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\AdminType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;


class AdminController extends AbstractController
{

    private $encoder;


    public function __construct(UserPasswordEncoderInterface $encoder)
    {
        $this->encoder = $encoder;


    }
   /**
    * @Route("/admin", name="admin_index", methods={"GET"})
    */
    public function index(): Response
    {
        $Admins = $this->getDoctrine()->getRepository(User::class)->findByRole("ROLE_ADMIN");

        return $this->render('back/admin/index.html.twig', [
            'admins' => $Admins,
        ]);
    }




    /**
     * @Route("/admin/newAdmin", name="admin_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $user = new User();
        $form = $this->createForm(AdminType::class, $user);
        $user->setRoles(array('ROLE_ADMIN'));

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $password = $form->getData()->getPassword();
            $form->getData()->setPassword($this->encoder->encodePassword($user, $password));
            $entityManager->persist($user);
            $entityManager->flush();
            return $this->redirectToRoute('admin_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('back/admin/new.html.twig', [
            'admin' => $user,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/admin/listEtudiant", name="etudiant_index", methods={"GET"})
     */
    public function list_etudiants(): Response
    {
        $Etudiants=$this->getDoctrine()->getRepository(User::class)->findBy(['role' => 1]);
        return $this->render('back/listEtudiants.html.twig', [
            'etudiants' => $Etudiants,
        ]);
    }


    /**
    * @Route("/admin/listEntreprise", name="entreprise_index", methods={"GET"})
    */
    public function list_entreprises(): Response
    {
        $Entreprises=$this->getDoctrine()->getRepository(User::class)->findBy(['role' => 2]);
        return $this->render('back/listEntreprises.html.twig', [
            'entreprises' => $Entreprises,
        ]);
    }


    /**
     * @Route("admin/search/{searchString}", name="search")
     */
    public function search($searchString): JsonResponse
    {
        $serializer = new Serializer([new ObjectNormalizer()]);
        $repository = $this->getDoctrine()->getRepository(User::class);
        $users = $repository->findBynom($searchString);
        $data=$serializer->normalize($users);
        return new JsonResponse($data);
    }

    /**
     * @Route("admin/listEntreprise/search/{searchString}", name="searchEnt")
     */
    public function searchEnt($searchString): JsonResponse
    {
        $serializer = new Serializer([new ObjectNormalizer()]);
        $repository = $this->getDoctrine()->getRepository(User::class);
        $users = $repository->findBynom($searchString);
        $data=$serializer->normalize($users);
        return new JsonResponse($data);
    }

    /**
     * @Route("admin/listEtudiant/search/{searchString}", name="searchEtu")
     */
    public function searchEtu($searchString): JsonResponse
    {
        $serializer = new Serializer([new ObjectNormalizer()]);
        $repository = $this->getDoctrine()->getRepository(User::class);
        $users = $repository->findBynom($searchString);
        $data=$serializer->normalize($users);
        return new JsonResponse($data);
    }
    
}
