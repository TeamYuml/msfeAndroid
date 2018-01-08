<?php

/**
 * Plik z webservicem dla aplikacji
 * harmonogramu usera na androida
 */
class Android extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        $this->load->database();
    }

    /**
     * Rejestracja u¿ytkownika
     */
    public function register()
    {
        $this->load->helper('url');

        $email = $this->input->post('email');
        $haslo = $this->input->post('haslo');
        $imie = $this->input->post('imie');
        $nazwisko = $this->input->post('nazwisko');
        $pesel = $this->input->post('pesel');
        $adres = $this->input->post('adres');

        // Selectowanie emaila w celu sprawdzenia czy taki jest juz w DB
        $this->db->select('emailUser');
        $this->db->where('emailUser', $email);
        $query = $this->db->get('usermsfe');

        if ($query->num_rows() == 1) {
            echo "Ten email juz istnieje";
        } else {

            // Sprawdzenie peselu czy istnieje w DB
            $this->db->select('PESELUser');
            $this->db->where('PESELUser', $pesel);
            $query = $this->db->get('usermsfe');
            if ($query->num_rows() == 1) {
                echo "Taki pesel juz istnieje";
            } else {
            
                $this->db->reset_query();

                // hashowanie hasla
                $hashedPassword = password_hash($haslo, PASSWORD_DEFAULT);

                // array z danymi ktore beda dodane do bazy danych
                $user = array(
                    'emailUser' => $email,
                    'hasloUser' => $hashedPassword,
                    'imieUser' => $imie,
                    'nazwiskoUser' => $nazwisko,
                    'PESELUser' => $pesel,
                    'adresUser' => $adres
                );

                // dodanie rekordu do bazy danych
                if ($this->db->insert('usermsfe', $user) === true) {
                    // select id nowo utworzonego rekordu
                    $insertID = $this->db->insert_id();

                    // pokazanie id uzytkownika
                    echo $insertID;
                } else {
                    echo "Cos poszlo nie tak";
                }
            }
        }
    }

    /**
     * Pobieranie harmonogramu u¿ytkownika
     * z bazy danych wystawionego przez lekarza
     */
    public function getHarmonogram()
    {
        $this->load->helper('url');

        $userID = $this->input->post('userID');

        $clickedDate = $this->input->post('clickedDate');

        // select harmonogramu usera
        $this->db->select('idHarmonogram');
        $this->db->where('idUser', $userID);
        $query = $this->db->get('harmonogram');

        if ($query->num_rows() != 1) {
            echo "Brak harmonogramu";
            // log something here
        } else {
            $row = $query->row();
            $harmonogramID = $row->idHarmonogram;
            $this->db->reset_query();

            // select lekow ktore sa powinny zostac pobrane odpowiedniego dnia
            // ktory wskazuje zmienna clickedDate
            $this->db->select('hgmain.godzinaPodania, leki.nazwaLeku');
            $this->db->from('harmonogrammain as hgmain');
            $this->db->join('leki', 'hgmain.idLeku = leki.idLeku');
            $this->db->where('idHarmonogramu', $harmonogramID);
            $this->db->where('dataStart <=', $clickedDate);
            $this->db->where('(dataStart + INTERVAL okres DAY) >=',
                $clickedDate);
            $this->db->order_by('godzinaPodania', 'ASC');
            $this->db->order_by('leki.nazwaLeku', 'ASC');
            $query = $this->db->get();

            if ($query->num_rows() == 0) {
                echo "Brak harmonogramu";
            } else {
                // wyswietlenie lekow oraz godziny ich pobrania
                foreach ($query->result() as $row) {
                    echo $row->godzinaPodania . ":".
                        $row->nazwaLeku . ",";
                }
            }
        }
    }

    /**
     * Logowanie u¿ytkownika
     */
    public function login()
    {
        $this->load->helper('url');

        $email = $this->input->post('email');

        $haslo = $this->input->post('haslo');

        $this->db->select('idUser, hasloUser');
        $this->db->from('usermsfe');
        $this->db->where('emailUser', $email);
        $query = $this->db->get();

        if ($query->num_rows() != 1) {
            echo "Zly email";
        } else {
            $row = $query->row();

            if (password_verify($haslo, $row->hasloUser)) {
                echo $row->idUser;
            } else {
                echo "Zly haslo";
            }
        }
    }
}

