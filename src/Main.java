import controller.ClienteController;
import controller.ContaBancariaController;
import controller.EnderecoController;
import controller.TransacaoController;
import model.ClienteModel;
import model.EnderecoModel;
import model.TipoConta;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClienteController clienteController = new ClienteController();
        EnderecoController enderecoController = new EnderecoController();
        ContaBancariaController contaBancariaController = new ContaBancariaController();
        TransacaoController transacaoController = new TransacaoController();

        int op = -1;

        while (op != 0) {
            System.out.println("\n---- MENU PRINCIPAL ----");
            System.out.println("0 - Sair");
            System.out.println("1 - Clientes");
            System.out.println("2 - Conta Bancária");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.println("\n---- MENU CLIENTES ----");
                    System.out.println("1 - Cadastro de Cliente");
                    System.out.println("2 - Buscar por CPF");
                    System.out.println("3 - Listar clientes");
                    System.out.println("4 - Deletar Cliente");
                    int opcliente = sc.nextInt();
                    sc.nextLine();

                    switch (opcliente) {
                        case 1:
                            System.out.println("Digite seu CPF: ");
                            String cpf = sc.nextLine();
                            System.out.println("Digite seu nome completo: ");
                            String nome = sc.nextLine();
                            System.out.println("Digite seu telefone: ");
                            String telefone = sc.nextLine();
                            System.out.println("Digite o seu logradouro: ");
                            String logradouro = sc.nextLine();
                            System.out.println("Digite o seu número: ");
                            int numero = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Digite o seu CEP: ");
                            String cep = sc.nextLine();
                            System.out.println("Digite o seu município: ");
                            String municipio = sc.nextLine();

                            EnderecoModel enderecoCadastro = new EnderecoModel(logradouro, numero, cep, municipio);
                            ClienteModel cliente = new ClienteModel(cpf, nome, telefone, enderecoCadastro);


                            enderecoController.cadastrarEndereco(enderecoCadastro);
                            clienteController.cadastrarCliente(cliente);
                            break;

                        case 2:
                            System.out.println("Digite o CPF para busca: ");
                            clienteController.buscarPorCPF(sc.nextLine());
                            break;

                        case 3:
                            List<ClienteModel> clientes = clienteController.listagemClientes();
                            for (ClienteModel c : clientes) {
                                System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | CPF: " + c.getCpf());
                                System.out.println("Endereço: " + c.getEndereco().getLogradouro() + ", " + c.getEndereco().getNumero());
                                System.out.println("----------------------------------");
                            }
                            break;

                        case 4:
                            System.out.println("Digite o CPF para remover: ");
                            clienteController.deletarCliente(sc.nextLine());
                            break;
                    }
                    break;

                case 2:
                    System.out.println("\n---- MENU OPERAÇÕES BANCÁRIAS ----");
                    System.out.println("1 - Abrir uma conta");
                    System.out.println("2 - Sacar");
                    System.out.println("3 - Depositar");
                    System.out.println("4 - Extrato");
                    System.out.println("5 - Encerrar conta");
                    int opcontas = sc.nextInt();
                    sc.nextLine();

                    switch (opcontas) {
                        case 1:
                            System.out.println("Digite o CPF do titular: ");
                            String cpfConta = sc.nextLine();
                            System.out.println("Tipo de conta: 1-Poupança | 2-Corrente");
                            int tipo = sc.nextInt();
                            TipoConta tipoConta = (tipo == 1) ? TipoConta.POUPANCA : TipoConta.CORRENTE;
                            contaBancariaController.cadastrarConta(cpfConta, tipoConta);
                            break;

                        case 2:
                            System.out.println("CPF da conta: ");
                            String cpfS = sc.nextLine();
                            System.out.println("Valor do saque: ");
                            double vS = sc.nextDouble();
                            transacaoController.saque(cpfS, vS);
                            break;

                        case 3:
                            System.out.println("CPF da conta: ");
                            String cpfD = sc.nextLine();
                            System.out.println("Valor do depósito: ");
                            double vD = sc.nextDouble();
                            transacaoController.deposito(cpfD, vD);
                            break;

                        case 4:
                            System.out.println("Digite o CPF para ver o extrato: ");
                            transacaoController.extrato(sc.nextLine());
                            break;

                        case 5:
                            System.out.println("Digite o CPF da conta a encerrar: ");
                            contaBancariaController.encerrarConta(sc.nextLine());
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                    break;
            }
        }
        System.out.println("Programa encerrado!");
        sc.close();
    }
}