package Pacote;

import java.util.*;
import java.util.Arrays;

class SistemaReservas {
    private Reserva[] reservas;
    private Reserva[] reservasCanceladas;
    private int nextId = 1; // Controlador de ID
    private int count;
    private int countCanceladas;
    private int totalQuartos;
    private double limiteOcupacao;

    public SistemaReservas(int capacidade, int totalQuartos, double limiteOcupacao) {
        reservas = new Reserva[capacidade];
        reservasCanceladas = new Reserva[capacidade];
        count = 0;
        countCanceladas = 0;
        this.totalQuartos = totalQuartos;
        this.limiteOcupacao = limiteOcupacao;
    }

    // Verifica se há conflito com as datas de reserva existentes para o mesmo quarto
    private boolean verificarConflito(Reserva novaReserva) {
        for (int i = 0; i < count; i++) {
            Reserva reservaExistente = reservas[i];
            if (reservaExistente.numeroQuarto == novaReserva.numeroQuarto) {
                // Checa se os intervalos de datas se sobrepõem
                if (!(novaReserva.dataCheckOut.compareTo(reservaExistente.dataCheckIn) <= 0 ||
                      novaReserva.dataCheckIn.compareTo(reservaExistente.dataCheckOut) >= 0)) {
                    return true; // Conflito encontrado
                }
            }
        }
        return false; // Sem conflitos
    }

    // Método para adicionar uma nova reserva
    public boolean adicionarReserva(Reserva novaReserva) {
        if (count >= reservas.length) {
            System.out.println("Erro: Capacidade máxima atingida.");
            return false;
        }

        if (verificarConflito(novaReserva)) {
            System.out.println("Erro: Conflito de reserva! O quarto " + novaReserva.numeroQuarto +
                    " já está reservado para as datas especificadas.");
            return false;
        }

        reservas[count] = novaReserva;
        count++;
        System.out.println("Reserva adicionada com sucesso para o quarto " + novaReserva.numeroQuarto + ".");
        
     // Checa automaticamente a taxa de ocupação após adicionar a reserva
        verificarTaxaOcupacao();
        
        return true;
    }
    
 // Método para verificar a taxa de ocupação e emitir alerta, se necessário
    private void verificarTaxaOcupacao() {
        double taxaAtual = (count / (double) totalQuartos) * 100;
        if (taxaAtual >= limiteOcupacao) {
            System.out.printf("⚠️ Alerta: Taxa de ocupação alta! (%d%% ocupados)\n", (int) taxaAtual);
            System.out.println("Planeje-se para a chegada de novos hóspedes.");
        }
    }

    // Método para exibir todas as reservas
    public void listarReservas() {
        if (count == 0) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        Arrays.sort(reservas, 0, count, (r1, r2) -> r1.dataCheckIn.compareTo(r2.dataCheckIn));
        for (int i = 0; i < count; i++) {
            System.out.println(reservas[i]);
        }
    }

    // Método para buscar uma reserva pelo CPF
    public Reserva buscarReservaPorCPF(String cpf) {
        for (int i = 0; i < count; i++) {
            if (reservas[i].cpf.equals(cpf)) {
                return reservas[i];
            }
        }
        return null;
    }
    
    public void consultarDisponibilidade(String dataInicio, String dataFim, String categoria, int[] quartos) {
        System.out.println("Quartos disponíveis para " + categoria + " de " + dataInicio + " a " + dataFim + ":");
        boolean encontrado = false;
        
        for (int quarto : quartos) {
            boolean disponivel = true;
            for (Reserva reserva : reservas) {
                if (reserva != null && reserva.getNumeroQuarto() == quarto &&
                    reserva.mesmaCategoria(categoria) && reserva.conflitaCom(dataInicio, dataFim)) {
                    disponivel = false;
                    break;
                }
            }
            if (disponivel) {
                encontrado = true;
                System.out.println("Quarto: " + quarto);
            }
        }
        if (!encontrado) {
            System.out.println("Nenhum quarto disponível encontrado.");
        }
    }


    // Método para cancelar uma reserva pelo CPF
    public boolean cancelarReserva(String cpf) {
        for (int i = 0; i < count; i++) {
            if (reservas[i].cpf.equals(cpf)) {
            	reservasCanceladas[countCanceladas++] = reservas[i]; // Adiciona ao histórico de cancelados
                reservas[i] = reservas[count - 1]; // Substitui pela última reserva
                reservas[count - 1] = null;
                count--;
                System.out.println("Reserva cancelada com sucesso.");
                
                // Recalcula a taxa de ocupação após o cancelamento
                verificarTaxaOcupacao();
                
                return true;
            }
        }
        System.out.println("Erro: Reserva não encontrada.");
        return false;
    }
    
    public Reserva buscarReservaPorID(int id) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == id) {
                return reserva;
            }
        }
        return null; // Retorna null se nenhuma reserva for encontrada
    }

    //Relatório: Taxa de ocupação para um período específico
    public void taxaOcupacao(String dataInicio, String dataFim, int totalQuartos) {
    	int diasOcupados = 0;
    	for (int i = 0; i < count; i++) {
    		Reserva reserva = reservas[i];
    		if (reserva.conflitaCom(dataInicio, dataFim)) {
    			diasOcupados++;
    		}
    	}
    	double taxaOcupacao = (diasOcupados / (double) totalQuartos) * 100;
    	System.out.printf("Taxa de ocupação no período (%s a %s): %.2f%%\n", dataInicio, dataFim, taxaOcupacao);
    }

    // Relatório: Quartos mais e menos reservados
    public void quartosMaisMenosReservados() {
    	Map<Integer, Integer> contadorReservas = new HashMap<>();
    	for (int i = 0; i < count; i++) {
    		int numeroQuarto = reservas[i].numeroQuarto;
    		contadorReservas.put(numeroQuarto, contadorReservas.getOrDefault(numeroQuarto, 0) + 1);
    	}

    	int maisReservado = Collections.max(contadorReservas.values());
    	int menosReservado = Collections.min(contadorReservas.values());

    	System.out.println("Quartos mais reservados:");
    	contadorReservas.forEach((quarto, count) -> {
    		if (count == maisReservado) {
    			System.out.printf("Quarto %d com %d reservas.\n", quarto, count);
    		}
    	});

    	System.out.println("Quartos menos reservados:");
    	contadorReservas.forEach((quarto, count) -> {
    		if (count == menosReservado) {
    			System.out.printf("Quarto %d com %d reservas.\n", quarto, count);
    		}
    	});
    }

    // Relatório: Número de cancelamentos em um determinado período
    public void numeroCancelamentos(String dataInicio, String dataFim) {
    	int cancelamentos = 0;
    	for (int i = 0; i < countCanceladas; i++) {
    		Reserva reserva = reservasCanceladas[i];
    		if (reserva.conflitaCom(dataInicio, dataFim)) {
    			cancelamentos++;
    		}
    	}
    	System.out.printf("Número de cancelamentos no período (%s a %s): %d\n", dataInicio, dataFim, cancelamentos);
	}
}