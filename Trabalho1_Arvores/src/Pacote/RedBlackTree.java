package Pacote;

import java.util.ArrayList;
import java.util.List;

class RedBlackTree {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	class Node {
        Reserva data;
        Node left, right, parent;
        boolean color;
        
        Node(Reserva data) {
            this.data = data;
            this.color = RED;
            this.left = this.right = this.parent = null;
        }
    }
	 
    private No root;
    private Node TNULL;
    private RedBlackTree historico;
    
 // Construtor padrão
    public RedBlackTree() {
    	TNULL = new Node(null);
        TNULL.color = BLACK;
        this.root = null;
        this.historico = null;
    }

    // Construtor com opção de histórico
    public RedBlackTree(boolean comHistorico) {
        this.root = null;
        if (comHistorico) {
            this.historico = new RedBlackTree(false); // Histórico sem outro histórico
        }
    }

    public void insert(Reserva reserva) {
        No newNode = new No(reserva);
        root = insertRec(root, newNode);
        fixInsert(newNode);
    }

    public void cancelarReserva(String cpf) {
        No nodeToDelete = findNodeByCPF(root, cpf);

        if (nodeToDelete == null) {
            System.out.println("Erro: Reserva com CPF " + cpf + " não encontrada.");
            return;
        }
        
     // Adiciona ao histórico
        if (historico != null) {
            historico.insert(nodeToDelete.reserva);
        }

        deleteNode(nodeToDelete);
        System.out.println("Reserva com CPF " + cpf + " foi cancelada com sucesso.");
    }
    
 // Listar histórico de reservas canceladas
    public void listarHistorico() {
        if (historico == null || historico.root == null) {
            System.out.println("Histórico vazio.");
        } else {
            System.out.println("Reservas Canceladas:");
            historico.inOrder();
        }
    }

    // Listar reservas ativas
    public void listarReservas() {
        if (root == null) {
            System.out.println("Nenhuma reserva cadastrada.");
        } else {
            System.out.println("Reservas Ativas:");
            inOrder();
        }
    }

    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(No node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.println(node.reserva);
            inOrderRec(node.right);
        }
    }
    
 // Buscar reserva por CPF
    public Reserva buscarReservaPorCPF(String cpf) {
        No node = findNodeByCPF(root, cpf);
        return node != null ? node.reserva : null;
    }

    // Buscar reserva por ID
    public Reserva buscarReservaPorID(int id) {
        No node = findNodeByID(root, id);
        return node != null ? node.reserva : null;
    }
    
 // Busca nó pelo CPF
    private No findNoByCPF(No current, String cpf) {
        if (current == null) return null;
        
        if (current.reserva.cpf.equals(cpf)) return current;

        if (cpf.compareTo(current.reserva.cpf) < 0) {
            return findNodeByCPF(current.left, cpf);
        } else {
            return findNodeByCPF(current.right, cpf);
        }
    }
    
 // Função auxiliar para buscar nó por ID
    private No findNodeByID(No current, int id) {
        if (current == null) return null;

        if (current.reserva.Id == id) return current;

        No leftResult = findNodeByID(current.left, id);
        if (leftResult != null) return leftResult;

        return findNodeByID(current.right, id);
    }

    private No insertRec(No root, No newNode) {
        if (root == null) {
            return newNode;
        }
        
        // Compara pela data de check-in
        if (newNode.reserva.dataInicio < root.reserva.dataInicio) {
            root.left = insertRec(root.left, newNode);
            root.left.parent = root;
        } else if (newNode.reserva.dataInicio > root.reserva.dataInicio) {
            root.right = insertRec(root.right, newNode);
            root.right.parent = root;
        } else {
        	if (newNode.reserva.cpf.compareTo(root.reserva.cpf) < 0) {
        		root.left = insertRec(root.left, newNode);
        		root.left.parent = root;
        	} else if (newNode.reserva.cpf.compareTo(root.reserva.cpf) > 0) {
        		root.right = insertRec(root.right, newNode);
        		root.right.parent = root;
        	}
        }
        return root;
    }
    
    public void consultarDisponibilidade(String dataInicio, String dataFim, String categoria, int[] quartos) {
        System.out.println("Quartos disponíveis para " + categoria + " de " + dataInicio + " a " + dataFim + ":");
        List<Integer> quartosDisponiveis = new ArrayList<>();

        for (int quarto : quartos) {
            if (quartoDisponivel(root, dataInicio, dataFim, categoria, quarto)) {
                quartosDisponiveis.add(quarto);
            }
        }

        if (quartosDisponiveis.isEmpty()) {
            System.out.println("Nenhum quarto disponível encontrado.");
        } else {
            for (int quarto : quartosDisponiveis) {
                System.out.println("Quarto: " + quarto);
            }
        }
    }

    private boolean quartoDisponivel(No node, String dataInicio, String dataFim, String categoria, int quarto) {
        if (node == null) return true;

        Reserva reserva = node.reserva;
        if (reserva.getNumeroQuarto() == quarto &&
            reserva.mesmaCategoria(categoria) && reserva.conflitaCom(dataInicio, dataFim)) {
            return false;
        }

        return quartoDisponivel(node.left, dataInicio, dataFim, categoria, quarto) &&
               quartoDisponivel(node.right, dataInicio, dataFim, categoria, quarto);
    }


    private void fixInsert(No node) {
        while (node.parent != null && node.parent.isRed) {
            if (node.parent == node.parent.parent.left) {
                No uncle = node.parent.parent.right;

                if (uncle != null && uncle.isRed) {
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rotateRight(node.parent.parent);
                }
            } else {
                No uncle = node.parent.parent.left;

                if (uncle != null && uncle.isRed) {
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.isRed = false;
    }

    private void deleteNode(No nodeToDelete) {
        No replacement;
        No fixNode;

        boolean originalColor = nodeToDelete.isRed;

        if (nodeToDelete.left == null) {
            replacement = nodeToDelete.right;
            transplant(nodeToDelete, nodeToDelete.right);
        } else if (nodeToDelete.right == null) {
            replacement = nodeToDelete.left;
            transplant(nodeToDelete, nodeToDelete.left);
        } else {
            No successor = getMinimum(nodeToDelete.right);
            originalColor = successor.isRed;
            fixNode = successor.right;

            if (successor.parent == nodeToDelete) {
                if (fixNode != null) {
                    fixNode.parent = successor;
                }
            } else {
                transplant(successor, successor.right);
                successor.right = nodeToDelete.right;
                successor.right.parent = successor;
            }

            transplant(nodeToDelete, successor);
            successor.left = nodeToDelete.left;
            successor.left.parent = successor;
            successor.isRed = nodeToDelete.isRed;
            replacement = fixNode;
        }

        if (!originalColor) {
            fixDelete(replacement);
        }
    }

    private No findNodeByCPF(No current, String cpf) {
        if (current == null) return null;

        if (current.reserva.cpf.equals(cpf)) return current;

        No leftResult = findNodeByCPF(current.left, cpf);
        if (leftResult != null) return leftResult;

        return findNodeByCPF(current.right, cpf);
    }

    private No getMinimum(No node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void transplant(No target, No replacement) {
        if (target.parent == null) {
            root = replacement;
        } else if (target == target.parent.left) {
            target.parent.left = replacement;
        } else {
            target.parent.right = replacement;
        }

        if (replacement != null) {
            replacement.parent = target.parent;
        }
    }

    private void fixDelete(No node) {
        while (node != root && (node == null || !node.isRed)) {
            if (node == node.parent.left) {
                No sibling = node.parent.right;

                if (sibling.isRed) {
                    sibling.isRed = false;
                    node.parent.isRed = true;
                    rotateLeft(node.parent);
                    sibling = node.parent.right;
                }

                if ((sibling.left == null || !sibling.left.isRed) &&
                        (sibling.right == null || !sibling.right.isRed)) {
                    sibling.isRed = true;
                    node = node.parent;
                } else {
                    if (sibling.right == null || !sibling.right.isRed) {
                        if (sibling.left != null) sibling.left.isRed = false;
                        sibling.isRed = true;
                        rotateRight(sibling);
                        sibling = node.parent.right;
                    }

                    sibling.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    if (sibling.right != null) sibling.right.isRed = false;
                    rotateLeft(node.parent);
                    node = root;
                }
            } else {
                No sibling = node.parent.left;

                if (sibling.isRed) {
                    sibling.isRed = false;
                    node.parent.isRed = true;
                    rotateRight(node.parent);
                    sibling = node.parent.left;
                }

                if ((sibling.right == null || !sibling.right.isRed) &&
                        (sibling.left == null || !sibling.left.isRed)) {
                    sibling.isRed = true;
                    node = node.parent;
                } else {
                    if (sibling.left == null || !sibling.left.isRed) {
                        if (sibling.right != null) sibling.right.isRed = false;
                        sibling.isRed = true;
                        rotateLeft(sibling);
                        sibling = node.parent.left;
                    }

                    sibling.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    if (sibling.left != null) sibling.left.isRed = false;
                    rotateRight(node.parent);
                    node = root;
                }
            }
        }

        if (node != null) node.isRed = false;
    }

    private void rotateLeft(No node) {
        No rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rotateRight(No node) {
        No leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }
}