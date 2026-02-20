package principal;

import apiclient.ApiCliente;
import menu.Menu;

public class Principal {

    public static void main(String[] args) {

        String apiKey = "4b4ca2e07c2750b0a6226fb1";

        ApiCliente apiCliente = new ApiCliente(apiKey);
        Menu menu = new Menu(apiCliente);

        menu.ejecutar();
    }
}