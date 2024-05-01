package org.trinidad.webapp.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "AsignaturaServlet", value = "AsignaturaServlet}")
public class AsignaturaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String url = "jdbc:mysql://127.0.0.1:3306/bdasignatura?user=root&password=TrinidadFerrando";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM asignatura");
                 ResultSet rs = pstmt.executeQuery()) {

                cabeceraHTML(out, "Listado de Asignaturas");
                tituloHTML(out, "Asignaturas Disponibles");
                tablaAsignaturas(out, rs);
                pieHTML(out);

            } catch (SQLException e) {
                e.printStackTrace();
                tituloHTML(out, "Error al conectar con la base de datos");
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private void cabeceraHTML(PrintWriter out, String titulo) {
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<META NAME='title' CONTENT='" + titulo + ", examen de Servlets Java y JDBC'>");
        out.println("<meta name='author' content='Trinidad Ferrando'>");
        out.println("<TITLE>" + titulo + "</TITLE>");
        out.println("<STYLE>");
        out.println("h3 { font-size: 12pt; font-family: Arial; color: white; background-color:#FFFFFF; line-height: 134%; }");
        out.println("text-align: justify; margin-top:6; margin-bottom:6; margin-left:6; margin-right:6;");
        out.println("h5 { color: #442628; text-align: left; text-indent:0px; margin-top:0; margin-right: 10; margin-left: 10;}");
        out.println("margin-bottom:0;background-color: white; font-size:12pt; font-family: Courier;}");
        out.println("h4 { font-size: 14pt; font-family: Arial; color: white; background-color:#442628; line-height: 134%; }");
        out.println("text-align: center; margin-top:6; margin-bottom:6; margin-left:6; margin-right:6;");
        out.println("p { text-align: justify; margin-top:6; margin-bottom:6; margin-left:10; margin-right:10;}");
        out.println("h1 { color: white; text-align: center; background-color: #442628; font size: 20pt; }");
        out.println("h2 { color: white; text-align: center; background-color: #442628; font size: 20pt; font-family: Courier;}");
        out.println("a.sub { text-decoration:underline; }");
        out.println("a { text-decoration:none; }");
        out.println("</STYLE>");
        out.println("</HEAD>");
        out.println("<BODY bgColor='#FFFFFF' text='#000000' link='#004466' vlink='#888888'>");
    }


    private void tituloHTML(PrintWriter out, String mensaje) {
        out.println("<h1><br/>" + mensaje + "<br/>&nbsp;</h1>");
    }

    private void tablaAsignaturas(PrintWriter out, ResultSet rs) throws SQLException {
        out.println("<h5><br/><center>");
        out.println("<table border='1' cellpadding='6' style='font-size: 10pt; font-family: Courier; color: white; background-color:#547cb4; line-height: 134%; text-align: justify; margin-top:6; margin-bottom:6; margin-left:20; margin-right:20'>");
        out.println("<tr><th>ID</th><th>Nombre</th><th>Materia</th><th>Correlatividad</th><th>Año</th><th>Obligatorio</th></tr>");
        while (rs.next()) {
            out.printf("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td><td>%d</td><td>%d</td></tr>",
                    rs.getInt("idAsignatura"), rs.getString("nombreAsignatura"), rs.getString("materiaAsignatura"), rs.getString("correlatividad"), rs.getInt("añoAsignatura"), rs.getInt("obligatorio"));
        }
        out.println("</table></center></h5>");
    }

    private void pieHTML(PrintWriter out) {
        out.println("<div id='nifty3'><table width='100%' bgcolor='#42628e'>");
        out.println("<tr><td width='20%'>&nbsp;</td><td width='60%'>");
        out.println("<p align='center'><h2 style='background-color:#FFFFFF'><a href='tu_email'>Contacto</a></h2></p>");
        out.println("</td><td width='20%'>&nbsp;</td></tr>");
        out.println("</table></div>");
        out.println("</BODY></HTML>");
    }
}

