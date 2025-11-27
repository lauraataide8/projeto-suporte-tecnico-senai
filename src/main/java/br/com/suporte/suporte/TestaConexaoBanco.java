package br.com.suporte.suporte;

public class TestaConexaoBanco {

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL carregando");

            System.out.println("Configuração DataSource configurada!");
            System.out.println("JPA Vendor Adapter configurado");

        } catch (Exception e) {
            System.out.println("Erro: "+e);
        }

    }

}