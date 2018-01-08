<?php
/**
 * Plik z zasadami walidacji
 */

$config = array(
        'register' => array(
            array(
                'field' => 'email',
                'label' => 'Email',
                'rules' => 'trim|required|valid_email|is_unique[Usermsfe.emailUser]'
            ),
            array(
                'field' => 'pwd',
                'label' => 'Haslo',
                'rules' => 'trim|required'
            ),
            array(
                'field' => 'confirmPwd',
                'label' => 'Potwierdz Haslo',
                'rules' => 'trim|required|matches[pwd]'
            ),
            array(
                'field' => 'imie',
                'label' => 'Imie',
                'rules' => 'trim|required'
            ),
            array(
                'field' => 'nazwisko',
                'label' => 'Nazwisko',
                'rules' => 'trim|required'
            ),
            array(
                'field' => 'PESEL',
                'label' => 'PESEL',
                'rules' => 'trim|required|numeric|exact_length[11]|is_unique[Usermsfe.PESELUser]'
            ),
            array(
                'field' => 'miasto',
                'label' => 'Miasto',
                'rules' => 'trim|required'
            ),
            array(
                'field' => 'ulica',
                'label' => 'Ulica',
                'rules' => 'trim|required'
            ),
            array(
                'field' => 'nrDomu',
                'label' => 'Numer domu',
                'rules' => 'trim|required'
            ),
            array(
                'field' => 'nrMieszkania',
                'label' => 'Numer mieszkania',
                'rules' => 'trim|required|numeric'
            )
        )
);
