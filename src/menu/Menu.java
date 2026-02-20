package menu;

import apiclient.ApiCliente;
import modelo.RespuestaApi;
import service.ConversorService;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private final ApiCliente apiCliente;
    private final ConversorService conversorService;
    private final Scanner scanner = new Scanner(System.in);
    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public Menu(ApiCliente apiCliente) {
        this.apiCliente = apiCliente;
        this.conversorService = new ConversorService();
    }

    public void ejecutar() {
        while (true) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");

            if (opcion == 0) {
                System.out.println("Saliendo... ");
                break;
            }

            switch (opcion) {
                case 1 -> convertir("USD", "COP");
                case 2 -> convertir("COP", "USD");
                case 3 -> convertir("USD", "BRL");
                case 4 -> convertir("BRL", "USD");
                case 5 -> convertir("USD", "ARS");
                case 6 -> convertir("ARS", "USD");
                case 7 -> convertirPersonalizado();
                default -> System.out.println("Opción inválida. Intenta de nuevo.\n");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("======================================");
        System.out.println("           MENÚ PRINCIPAL             ");
        System.out.println("======================================");
        System.out.println("1) De Dólar (USD) a Peso Colombiano (COP)");
        System.out.println("2) De Peso Colombiano (COP) a Dólar (USD)");
        System.out.println("3) De Dólar (USD) a Real Brasileño (BRL)");
        System.out.println("4) De Real Brasileño (BRL) a Dólar (USD)");
        System.out.println("5) De Dólar (USD) a Peso Argentino (ARS)");
        System.out.println("6) De Peso Argentino (ARS) a Dólar (USD)");
        System.out.println("7) Elegir monedas manualmente");
        System.out.println("0) Salir");
        System.out.println("--------------------------------------");
    }

    private void convertir(String base, String objetivo) {
        try {
            double cantidad = leerDoublePositivo("Cantidad a convertir: ");

            RespuestaApi datos = apiCliente.obtenerDatos(base);
            Map<String, Double> tasas = datos.getTasas();

            Double tasa = tasas.get(objetivo);
            if (tasa == null) {
                System.out.println("No se encontró la moneda objetivo: " + objetivo + "\n");
                return;
            }

            double resultado = conversorService.convertir(tasa, cantidad);
            mostrarResultado(base, objetivo, tasa, cantidad, resultado);

        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage() + "\n");
        }
    }

    private void convertirPersonalizado() {
        try {
            String base = leerMoneda("Moneda base (ej: USD): ");
            String objetivo = leerMoneda("Moneda objetivo (ej: COP): ");
            double cantidad = leerDoublePositivo("Cantidad a convertir: ");

            RespuestaApi datos = apiCliente.obtenerDatos(base);
            Map<String, Double> tasas = datos.getTasas();

            Double tasa = tasas.get(objetivo);
            if (tasa == null) {
                System.out.println("No se encontró la moneda objetivo: " + objetivo + "\n");
                return;
            }

            double resultado = conversorService.convertir(tasa, cantidad);
            mostrarResultado(base, objetivo, tasa, cantidad, resultado);

        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage() + "\n");
        }
    }

    private void mostrarResultado(String base, String objetivo, double tasa, double cantidad, double resultado) {
        System.out.println("\n===== RESULTADO =====");
        System.out.println("Base: " + base);
        System.out.println("Objetivo: " + objetivo);
        System.out.println("Tasa: " + tasa);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Convertido: " + df.format(resultado));
        System.out.println();
    }


    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número entero válido.\n");
            }
        }
    }

    private double leerDoublePositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim().replace(",", ".");
            try {
                double valor = Double.parseDouble(input);
                if (valor <= 0) {
                    System.out.println("La cantidad debe ser mayor que 0.\n");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida. Ingresa un número (ej: 100 o 100.5).\n");
            }
        }
    }

    private String leerMoneda(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String moneda = scanner.nextLine().trim().toUpperCase();
            if (moneda.length() == 3 && moneda.chars().allMatch(Character::isLetter)) {
                return moneda;
            }
            System.out.println("Código inválido. Debe ser de 3 letras (ej: USD, COP, EUR).\n");
        }
    }
}