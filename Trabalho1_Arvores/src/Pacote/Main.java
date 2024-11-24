package Pacote;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-Vindo ao hotel Boa Vista, Escolha o sistema de gerenciamento de reservas:");
        System.out.println("1 - Baseado em Array");
        System.out.println("2 - Baseado em Árvore Rubro-Negra");

        int escolha = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        if (escolha == 1) {
            // Sistema baseado em Array
            SistemaReservas sistema = new SistemaReservas(20, 10, 100.0);

            // Adicionando reservas
            sistema.adicionarReserva(new Reserva("12345678900", "Homem de Ferro", 101, "2023-12-01", "2023-12-05", "Luxo"));
            sistema.adicionarReserva(new Reserva("98765432100", "Chapolin Colorado", 102, "2023-12-02", "2023-12-06", "Econômico"));
            sistema.adicionarReserva(new Reserva("11122233344", "Batman", 103, "2023-12-01", "2023-12-04", "Luxo"));

            while (true) {
                System.out.println("\n==== Escolha uma opção: ====");
                System.out.println("1 - Listar reservas ordenadas por data de check-in");
                System.out.println("2 - Cancelar reserva");
                System.out.println("3 - Consultar reserva por CPF");
                System.out.println("4 - Consultar reserva por ID");
                System.out.println("5 - Consulta de Disponibilidade de Quartos");
                System.out.println("6 - Relatórios Gerenciais");
                System.out.println("7 - Cadastrar Nova Reserva");
                System.out.println("0 - Sair");

                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir quebra de linha

                switch (opcao) {
                    case 1:
                        System.out.println("\nReservas ordenadas por data de check-in:");
                        sistema.listarReservas();
                        break;

                    case 2:
                        System.out.print("Informe o CPF da reserva a ser cancelada: ");
                        String cpfParaCancelar = scanner.nextLine();
                        sistema.cancelarReserva(cpfParaCancelar);
                        break;

                    case 3:
                        System.out.print("Informe o CPF para consulta: ");
                        String cpfConsulta = scanner.nextLine();
                        Reserva reservaEncontrada = sistema.buscarReservaPorCPF(cpfConsulta);
                        if (reservaEncontrada != null) {
                            System.out.println("Reserva encontrada:\n" + reservaEncontrada);
                        } else {
                            System.out.println("Reserva não encontrada para o CPF informado.");
                        }
                        break;
                        
                    case 4: 
                    	System.out.print("Informe o ID para consulta: ");
                        int IdConsulta = scanner.nextInt();
                        scanner.nextLine(); // Consumir quebra de linha
                        Reserva reservaPorID = sistema.buscarReservaPorID(IdConsulta);
                        if (reservaPorID != null) {
                            System.out.println("Reserva encontrada:\n" + reservaPorID);
                        } else {
                            System.out.println("Reserva não encontrada para o ID informado.");
                        }
                        break;

                    case 5:
                        System.out.println("Consulta de Disponibilidade de Quartos");
                        System.out.print("Informe a data de início (DD-MM-YYYY): ");
                        String dataInicio = scanner.nextLine();
                        System.out.print("Informe a data de fim (DD-MM-YYYY): ");
                        String dataFim = scanner.nextLine();
                        System.out.print("Informe a categoria do quarto (Luxo/Econômico/Presidencial): ");
                        String categoria = scanner.nextLine();

                        int[] quartosArray = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111}; // Exemplos de quartos no hotel
                        sistema.consultarDisponibilidade(dataInicio, dataFim, categoria, quartosArray);
                        break;
                        
                    case 6:
                        System.out.println("\nRelatórios Gerenciais:");
                        System.out.println("1 - Taxa de Ocupação");
                        System.out.println("2 - Quartos mais e menos reservados");
                        System.out.println("3 - Número de cancelamentos");

                        int opcaoRelatorio = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoRelatorio) {
                            case 1:
                                System.out.print("Informe a data de início (DD-MM-YYYY): ");
                                String inicioTaxa = scanner.nextLine();
                                System.out.print("Informe a data de fim (DD-MM-YYYY): ");
                                String fimTaxa = scanner.nextLine();
                                System.out.print("Informe o número total de quartos no hotel: ");
                                int totalQuartos = scanner.nextInt();
                                scanner.nextLine();
                                sistema.taxaOcupacao(inicioTaxa, fimTaxa, totalQuartos);
                                break;

                            case 2:
                                sistema.quartosMaisMenosReservados();
                                break;

                            case 3:
                                System.out.print("Informe a data de início (DD-MM-YYYY): ");
                                String inicioCancel = scanner.nextLine();
                                System.out.print("Informe a data de fim (DD-MM-YYYY): ");
                                String fimCancel = scanner.nextLine();
                                sistema.numeroCancelamentos(inicioCancel, fimCancel);
                                break;

                            default:
                                System.out.println("Opção inválida.");
                                break;
                        }
                        break;
                        
                    case 7: // Nova opção: Cadastrar Pessoas
                        System.out.println("\nCadastrar Nova Reserva:");
                        System.out.print("Informe o CPF: ");
                        String cpfNovo = scanner.nextLine();

                        System.out.print("Informe o Nome: ");
                        String nomeNovo = scanner.nextLine();

                        System.out.print("Informe o ID do Quarto: ");
                        int idQuartoNovo = scanner.nextInt();
                        scanner.nextLine(); // Consumir quebra de linha

                        System.out.print("Informe a Data de Check-in (DD-MM-YYYY): ");
                        String checkInNovo = scanner.nextLine();

                        System.out.print("Informe a Data de Check-out (DD-MM-YYYY): ");
                        String checkOutNovo = scanner.nextLine();

                        System.out.print("Informe a Categoria (Luxo/Econômico/Presidencial): ");
                        String categoriaNova = scanner.nextLine();

                        // Criar nova reserva e adicioná-la ao sistema baseado em Array
                        sistema.adicionarReserva(new Reserva(cpfNovo, nomeNovo, idQuartoNovo, checkInNovo, checkOutNovo, categoriaNova));
                        System.out.println("Reserva cadastrada com sucesso!");
                        break;

                    case 0:
                        System.out.println("Saindo do sistema.");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }

        } else if (escolha == 2) {
            // Sistema baseado em Árvore Rubro-Negra
            RedBlackTree tree = new RedBlackTree(true);

            // Inserindo reservas
            tree.insert(new Reserva("12345678900", "Lucas Miles", 101, "2023-12-01", "2023-12-05", "Luxo"));
            tree.insert(new Reserva("98765432100", "Gabigol", 102, "2023-12-02", "2023-12-06", "Econômico"));
            tree.insert(new Reserva("11122233344", "Felipão", 103, "2023-12-01", "2023-12-04", "Luxo"));
            tree.insert(new Reserva("12345678900", "Ronaldinho Gaucho", 104, "2023-12-01", "2023-12-05", "Luxo"));
            tree.insert(new Reserva("98765432100", "Neymar", 105, "2023-12-02", "2023-12-06", "Econômico"));
            tree.insert(new Reserva("11122233344", "Supla", 106, "2023-12-01", "2023-12-04", "Luxo"));
            tree.insert(new Reserva("12345678900", "Celso Portiolli", 107, "2023-12-01", "2023-12-05", "Luxo"));
            tree.insert(new Reserva("98765432100", "Fabio Alexandrini", 108, "2023-12-02", "2023-12-06", "Presidencial"));
            tree.insert(new Reserva("11122233344", "Daniel Gomes Soares", 109, "2023-12-01", "2023-12-04", "Presidencial"));
            tree.insert(new Reserva("12345678900", "Steve Jobs", 110, "2023-12-01", "2023-12-05", "Luxo"));
            tree.insert(new Reserva("98765432100", "Bertino de Souza", 111, "2023-12-02", "2015-09-21", "Econômico"));

            while (true) {
                System.out.println("\n==== Escolha uma opção: ====");
                System.out.println("1 - Listar reservas ativas");
                System.out.println("2 - Cancelar reserva");
                System.out.println("3 - Consultar reserva por CPF");
                System.out.println("4 - Consultar reserva por ID");
                System.out.println("5 - Listar histórico de reservas canceladas");
                System.out.println("6 - Consulta de Disponibilidade de Quartos");
                System.out.println("7 - Cadastrar Nova Reserva");
                System.out.println("0 - Sair");

                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir quebra de linha

                switch (opcao) {
                    case 1:
                        System.out.println("\nReservas Ativas:");
                        tree.listarReservas();
                        break;

                    case 2:
                        System.out.print("Informe o CPF da reserva a ser cancelada: ");
                        String cpfParaCancelar = scanner.nextLine();
                        tree.cancelarReserva(cpfParaCancelar);
                        break;

                    case 3:
                        System.out.print("Informe o CPF para consulta: ");
                        String cpfConsulta = scanner.nextLine();
                        Reserva reservaPorCPF = tree.buscarReservaPorCPF(cpfConsulta);
                        if (reservaPorCPF != null) {
                            System.out.println("Reserva encontrada:\n" + reservaPorCPF);
                        } else {
                            System.out.println("Reserva não encontrada para o CPF informado.");
                        }
                        break;

                    case 4:
                        System.out.print("Informe o ID para consulta: ");
                        int IdConsulta = scanner.nextInt();
                        Reserva reservaPorID = tree.buscarReservaPorID(IdConsulta);
                        if (reservaPorID != null) {
                            System.out.println("Reserva encontrada:\n" + reservaPorID);
                        } else {
                            System.out.println("Reserva não encontrada para o ID informado.");
                        }
                        break;

                    case 5:
                        System.out.println("\nHistórico de Reservas Canceladas:");
                        tree.listarHistorico();
                        break;

                    case 6:
                        System.out.println("Consulta de Disponibilidade de Quartos");
                        System.out.print("Informe a data de início (DD-MM-YYYY): ");
                        String dataInicio = scanner.nextLine();
                        System.out.print("Informe a data de fim (DD-MM-YYYY): ");
                        String dataFim = scanner.nextLine();
                        System.out.print("Informe a categoria do quarto (Luxo/Econômico/Presidencial): ");
                        String categoria = scanner.nextLine();

                        int[] quartosTree = {101, 102, 103, 104, 105}; // Exemplos de quartos no hotel
                        tree.consultarDisponibilidade(dataInicio, dataFim, categoria, quartosTree);
                        break;
                        
                    case 7: // Nova opção: Cadastrar Pessoas
                        System.out.println("\nCadastrar Nova Reserva:");
                        System.out.print("Informe o CPF: ");
                        String cpfNovoTree = scanner.nextLine();

                        System.out.print("Informe o Nome: ");
                        String nomeNovoTree = scanner.nextLine();

                        System.out.print("Informe o ID do Quarto: ");
                        int idQuartoNovoTree = scanner.nextInt();
                        scanner.nextLine(); // Consumir quebra de linha

                        System.out.print("Informe a Data de Check-in (DD-MM-YYYY): ");
                        String checkInNovoTree = scanner.nextLine();

                        System.out.print("Informe a Data de Check-out (DD-MM-YYYY): ");
                        String checkOutNovoTree = scanner.nextLine();

                        System.out.print("Informe a Categoria (Luxo/Econômico/Presidencial): ");
                        String categoriaNovaTree = scanner.nextLine();

                        // Criar nova reserva e adicioná-la à árvore Rubro-Negra
                        tree.insert(new Reserva(cpfNovoTree, nomeNovoTree, idQuartoNovoTree, checkInNovoTree, checkOutNovoTree, categoriaNovaTree));
                        System.out.println("Reserva cadastrada com sucesso!");
                        break;

                    case 0:
                        System.out.println("Saindo do sistema.");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } else {
            System.out.println("Escolha inválida!");
        }

        scanner.close();
    }
}