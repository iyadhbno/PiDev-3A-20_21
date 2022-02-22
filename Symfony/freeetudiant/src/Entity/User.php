<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Serializer\Annotation\Groups;

use App\Validators as MyValidate;




/**
 * User
 *
 *
 *
 * @ORM\Table(name="user", indexes={@ORM\Index(name="user_role", columns={"role"})})
 * @ORM\Entity
 *
 *
 *  @UniqueEntity(
 *     fields={"email"},
 *     message="L'adresse Email deja existe."
 * )
 *
 * @ORM\Entity (repositoryClass="App\Repository\UserRepository")
 */
class User implements UserInterface,\Serializable
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="bigint", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups ("post:read")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=100, nullable=false)
     * @MyValidate\VerifNull(groups={"modifyEtudiant"})
     * @Groups ("post:read")
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="email", type="string", length=50, nullable=false)
     * @MyValidate\VerifEmail
     * @Groups ("post:read")
     */
    private $email;

    /**
     * @var string
     *
     * @ORM\Column(name="password", type="string", length=255, nullable=false)
     * @MyValidate\VerifPassword(groups={"resetPassword"})
     * @Groups ("post:read")
     */
    private $password;

    /**
     * @var string
     *
     * @ORM\Column(name="tel", type="string", length=8, nullable=false)
     * @MyValidate\VerifTel(groups={"modifyEtudiant"})
     * @Groups ("post:read")
     */
    private $tel;

    /**
     * @var string
     *
     * @ORM\Column(name="photo", type="string", length=255, nullable=false)
     * (groups={"modifyEtudiant"})
     * @Groups ("post:read")
     */
    private $photo;

    /**
     * @var string|null
     *
     * @ORM\Column(name="cv", type="string", length=255, nullable=true)
     * (groups={"modifyEtudiant"})
     * @Groups ("post:read")
     */
    private $cv;

    /**
     * @var string|null
     *
     * @ORM\Column(name="offre", type="string", length=255, nullable=true)
     * @Groups ("post:read")
     */
    private $offre;

    /**
     * @var bool
     *
     * @ORM\Column(name="etatCompte", type="boolean", nullable=false, options={"default"="1"})
     * @Groups ("post:read")
     */
    private $etatcompte = true;

    /**
     * @var Role
     *
     * @ORM\ManyToOne(targetEntity="Role")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="role", referencedColumnName="id")
     * })
     * @Groups ("post:read")
     */
    private $role;

    /**
     * @var string
     *
     * @ORM\Column(name="roles", type="json")
     * @Groups ("post:read")

     */
    private $roles;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups ("post:read")
     */
    private $activation_token;




    public function getId(): ?string
    {
        return $this->id;
    }
    public function setId(string $id): self
    {
        $this->id = $id;

        return $this;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    public function getTel(): ?string
    {
        return $this->tel;
    }

    public function setTel(string $tel): self
    {
        $this->tel = $tel;

        return $this;
    }

    public function getPhoto(): ?string
    {
        return $this->photo;
    }

    public function setPhoto(string $photo): self
    {
        $this->photo = $photo;

        return $this;
    }

    public function getCv(): ?string
    {
        return $this->cv;
    }

    public function setCv(?string $cv): self
    {
        $this->cv = $cv;

        return $this;
    }

    public function getOffre(): ?string
    {
        return $this->offre;
    }

    public function setOffre(?string $offre): self
    {
        $this->offre = $offre;

        return $this;
    }

    public function getEtatcompte(): ?bool
    {
        return $this->etatcompte;
    }

    public function setEtatcompte(bool $etatcompte): self
    {
        $this->etatcompte = $etatcompte;

        return $this;
    }

    public function getRole(): ?Role
    {
        return $this->role;
    }

    public function setRole(?Role $role): self
    {
        $this->role = $role;

        return $this;
    }


    public function getRoles(): array
    {
        $roles = $this->roles;
        $roles[] = 'ROLE_USER';
        return array_unique($roles);
    }

    public function setRoles(array $roles): self
    {
        $this->roles = $roles;

        return $this;
    }
    public function getSalt()
    {
        // TODO: Implement getSalt() method.
    }

    public function getUsername()
    {
        return $this->id;

    }

    public function eraseCredentials()
    {
        // TODO: Implement eraseCredentials() method.
    }

    public function serialize()
    {
        return serialize(
            [
                $this->id,
                $this->nom,
                $this->email,
                $this->password,
                $this->tel,
                $this->role
            ]

        );
        // TODO: Implement serialize() method.
    }

    public function unserialize($string)
    {
        list(
            $this->id,
            $this->nom,
            $this->email,
            $this->password,
            $this->tel,
            $this->role

            )=unserialize($string,['allowed_classes'=>false]);}

        function constructEtu($nom,$email,$password,$tel,$photo,$cv,$roles) {
            $this->setNom($nom);
            $this->setEmail($email);
            $this->setPassword($password);
            $this->setTel($tel);
            $this->setPhoto($photo);
            $this->setCv($cv);
            $this->setRoles($roles);


        }

    public function getActivationToken(): ?string
    {
        return $this->activation_token;
    }

    public function setActivationToken(?string $activation_token): self
    {
        $this->activation_token = $activation_token;

        return $this;
    }

}
