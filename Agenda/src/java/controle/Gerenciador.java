package controle;

import dados.Tarefa;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Gerenciador", urlPatterns = {"/tarefas"})
public class Gerenciador extends HttpServlet {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        //cria a sessao        
        HttpSession sessao = request.getSession();

        //criacao da lista
        ArrayList<Tarefa> lista = (ArrayList<Tarefa>) sessao.getAttribute("listaTarefas");
        if (lista == null) {
            lista = new ArrayList<Tarefa>();
            sessao.setAttribute("listaTarefas", lista);
        }

        //pega os forms de name acao e posicao de cada um
        String acao = request.getParameter("acao");
        int pos = Integer.parseInt(request.getParameter("posicao"));

        if ("excluir".equals(acao)) {
            //esse if de baixo é pra garantir q ngm vai setar os parametros na url, 
            //seila, uma posicao com valor 1000
            if (pos >= 0 && pos < lista.size()) {
                lista.remove(pos);
            }
            response.sendRedirect("index.jsp");
            return;
        }
        if ("confirmar".equals(acao)) {
            if (pos >= 0 && pos < lista.size()) {
                Tarefa t = lista.get(pos);
                t.setTerminada(true);
            }
            response.sendRedirect("index.jsp");
            return;
        }
        if ("editar".equals(acao)) {
            if (pos >= 0 && pos < lista.size()) {
                sessao.setAttribute("tarefaEdicao", lista.get(pos));
            }
        }

        try {
            
            //se ele apertar no botao de cancelar enquanto edita a tarefa
            if (request.getParameter("cancelar") != null) {
                sessao.removeAttribute("tarefaEdicao");
                response.sendRedirect("index.jsp");
                return;
            }
            
            //se ele apertar em alterar a tarefa
            
            //pega pelos parametros os valores
            String descricao = request.getParameter("descr");
            String responsavel = request.getParameter("quem");
            Date prazo = sdf.parse(request.getParameter("prazo"));
            
            //verificacao de existencia dos parametros
            if (descricao == null || descricao.trim().isEmpty()) {
                throw new Exception("Descrição não pode ser vazia.");
            }
            if (responsavel == null || responsavel.trim().isEmpty()) {
                throw new Exception("Responsável deve ser informado.");
            }
            Date agora = new Date();
            if (prazo.before(agora)) {
                throw new Exception("Prazo deve ser futuro.");
            }

            //pega a lista na sessao pela posicao
            Tarefa edicao = (Tarefa) sessao.getAttribute("tarefaEdicao");
            if (edicao == null) {
                lista.add(new Tarefa(descricao, responsavel, prazo));
            } else {
                //quando clicar em alterar a tarefa atualiza a lista com os novos valores
                edicao.setDescricao(descricao);
                edicao.setResponsavel(responsavel);
                edicao.setPrazo(prazo);
                sessao.removeAttribute("tarefaEdicao");//para voltar ao modo de inserir dados no forumlario
            }

        } catch (ParseException pe) {
            sessao.setAttribute("msgErro",
                    "Data informada está incorreta.");
        } catch (Exception ex) {
            sessao.setAttribute("msgErro", ex.getMessage());
        }
        response.sendRedirect("index.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
