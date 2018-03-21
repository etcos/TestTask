/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.profitcalc.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import org.json.JSONObject;

/**
 *
 * @author evgeniy
 */
public class ProfitCalcMain extends javax.swing.JDialog {
    
    BigDecimal RATE = new BigDecimal(1.005);
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    
    private JFormattedTextField dateText;

    /**
     * Creates new form ProfiCalcMain
     */
    public ProfitCalcMain(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        dateText = (JFormattedTextField)dateChooser.getDateEditor();
        
        try{
            dateText.setFormatterFactory(getDateFormatterFactory());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        cleanButton.addActionListener((e) -> cleanBtn());
        closeButton.addActionListener((e) -> closeBtn());
        calcButton.addActionListener((e) -> runCalcBtn());
    }
    
    private DefaultFormatterFactory getDateFormatterFactory() throws ParseException{
        MaskFormatter dateFormatter = new MaskFormatter("##.##.####");
        dateFormatter.setValidCharacters("0123456789");
        dateFormatter.setPlaceholderCharacter('_');
        dateFormatter.setValueClass(String.class);
        DefaultFormatterFactory dateFormatterFactory = new DefaultFormatterFactory(dateFormatter);
        return dateFormatterFactory;
    }
    
    private boolean checkData() {
        if(dateText.getText().equals("__.__.____")) {
            JOptionPane.showMessageDialog(this, "Необходимо выбрать дату", "Внимание!", JOptionPane.INFORMATION_MESSAGE);
        } else if(sumFormattedTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Необходимо ввести сумму", "Внимание!", JOptionPane.INFORMATION_MESSAGE);
        } else if (dateChooser.getDate().after(new Date())) {
            JOptionPane.showMessageDialog(this, "Дата не может быть больше текущей", "Внимание!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            return true;
        }
        return false;
    }
    
    private URL createUrl(String link) {
        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private String parseUrl(URL url) {
        if (url == null) {
            return "";
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    
    private String createQuery(Date date) {
        StringBuilder request = new StringBuilder();
        request
                .append("http://api.fixer.io/")
                .append(f.format(date))
                .append("?base=USD&symbols=RUB");
        
        return request.toString();
    }
    
    private BigDecimal parseJson(String resultJson) {
        JSONObject ratesObj = new JSONObject(resultJson);
        JSONObject rates = (JSONObject) ratesObj.get("rates");
        
        return new BigDecimal(rates.get("RUB").toString());
        
    }
    
    private void runCalcBtn() {
        if (checkData()) {
            String resultJson = null;
            
            resultJson = parseUrl(createUrl(createQuery(dateChooser.getDate())));
            BigDecimal pastRubOne = parseJson(resultJson);
            
            resultJson = parseUrl(createUrl(createQuery(new Date())));
            BigDecimal currentRubOne = parseJson(resultJson);
            
            BigDecimal amount = new BigDecimal(sumFormattedTextField.getText());
            
            BigDecimal totalPastRub = pastRubOne.multiply(amount).multiply(RATE);
            
            BigDecimal totalCurrentRub = currentRubOne.multiply(amount).multiply(RATE);
            
            resultTextArea.setText("Стоимость 1 USD на выбранную дату:\n");
            resultTextArea.append(pastRubOne.toString() + " RUB\n");
            resultTextArea.append("Общая стоимость с комиссией:\n");
            resultTextArea.append(totalPastRub.setScale(2, RoundingMode.DOWN).toString() + " RUB\n");
            resultTextArea.append("Стоимость 1 USD на текущую дату:\n");
            resultTextArea.append(currentRubOne.toString() + " RUB\n");
            resultTextArea.append("Общая стоимость с комиссией:\n");
            resultTextArea.append(totalCurrentRub.setScale(2, RoundingMode.DOWN).toString() + " RUB\n");
            resultTextArea.append("Прибыль(убыток):\n");
            resultTextArea.append(totalCurrentRub.subtract(totalPastRub).setScale(2, RoundingMode.DOWN).toString() + " RUB");
            
        }
    }
    
    private void cleanBtn() {
        dateChooser.setDate(null);
        sumFormattedTextField.setText(null);
        resultTextArea.setText(null);
    }
    
    private void closeBtn() {
        System.exit(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        closeButton = new javax.swing.JButton();
        calcButton = new javax.swing.JButton();
        cleanButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        dataLabel = new javax.swing.JLabel();
        sumLabel = new javax.swing.JLabel();
        resultLabel = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        sumFormattedTextField = new javax.swing.JFormattedTextField();
        resultScrollPane = new javax.swing.JScrollPane();
        resultTextArea = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Расчёт");
        setName("ProfiCalcMain"); // NOI18N

        closeButton.setText("Закрыть");
        closeButton.setName("closeButton"); // NOI18N
        closeButton.setPreferredSize(new java.awt.Dimension(90, 35));

        calcButton.setText("Расчёт");
        calcButton.setName("calcButton"); // NOI18N
        calcButton.setPreferredSize(new java.awt.Dimension(90, 35));

        cleanButton.setText("Очистить");
        cleanButton.setName("cleanButton"); // NOI18N
        cleanButton.setPreferredSize(new java.awt.Dimension(90, 35));

        mainPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.GridBagLayout());

        dataLabel.setText("Дата:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        mainPanel.add(dataLabel, gridBagConstraints);

        sumLabel.setText("Сумма:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        mainPanel.add(sumLabel, gridBagConstraints);

        resultLabel.setText("Итог:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        mainPanel.add(resultLabel, gridBagConstraints);

        dateChooser.setMinimumSize(new java.awt.Dimension(150, 35));
        dateChooser.setName("dateChooser"); // NOI18N
        dateChooser.setPreferredSize(new java.awt.Dimension(150, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        mainPanel.add(dateChooser, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        mainPanel.add(filler1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        mainPanel.add(filler2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        mainPanel.add(filler3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        mainPanel.add(filler4, gridBagConstraints);

        sumFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        sumFormattedTextField.setMinimumSize(new java.awt.Dimension(135, 35));
        sumFormattedTextField.setName("sumFormattedTextField"); // NOI18N
        sumFormattedTextField.setPreferredSize(new java.awt.Dimension(135, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        mainPanel.add(sumFormattedTextField, gridBagConstraints);

        resultScrollPane.setName("resultScrollPane"); // NOI18N

        resultTextArea.setEditable(false);
        resultTextArea.setColumns(15);
        resultTextArea.setLineWrap(true);
        resultTextArea.setRows(9);
        resultTextArea.setTabSize(5);
        resultTextArea.setWrapStyleWord(true);
        resultTextArea.setName("resultTextArea"); // NOI18N
        resultScrollPane.setViewportView(resultTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        mainPanel.add(resultScrollPane, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        mainPanel.add(jSeparator1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        mainPanel.add(jSeparator2, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(calcButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cleanButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cleanButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProfitCalcMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProfitCalcMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProfitCalcMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProfitCalcMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProfitCalcMain dialog = new ProfitCalcMain(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calcButton;
    private javax.swing.JButton cleanButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel dataLabel;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel resultLabel;
    private javax.swing.JScrollPane resultScrollPane;
    private javax.swing.JTextArea resultTextArea;
    private javax.swing.JFormattedTextField sumFormattedTextField;
    private javax.swing.JLabel sumLabel;
    // End of variables declaration//GEN-END:variables
}
