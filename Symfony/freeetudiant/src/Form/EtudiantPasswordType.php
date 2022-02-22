<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;

class EtudiantPasswordType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder


            ->add('password', RepeatedType::class, [
                'type' => PasswordType::class,
                'invalid_message' => 'les deux mot de passe ne correspond pas.',
                'options' => ['attr' => ['class' => 'password-field']],
                'first_options'  => ['label' => '.'],
                'second_options' => ['label' => '.'],
            ])


        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => User::class,
            'validation_groups' => ['resetPassword'],

        ]);
    }
}
