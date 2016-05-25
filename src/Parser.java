import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by m.farsiabi on 5/23/2016.
 */

public class Parser {

    private List<Deposit> depositList = new ArrayList<Deposit>();

    Parser() {
        try {
            File fXmlFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("deposit");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String customerNum = null;
                    String depositType = null;
                    String depositBalance = null;
                    int duration = 0;

                    if (eElement.getElementsByTagName("customerNumber").getLength() > 0)
                        customerNum = eElement.getElementsByTagName("customerNumber").item(0).getTextContent();
                    if (eElement.getElementsByTagName("depositType").getLength() > 0)
                        depositType = eElement.getElementsByTagName("depositType").item(0).getTextContent();
                    if (eElement.getElementsByTagName("depositBalance").getLength() > 0)
                        depositBalance = eElement.getElementsByTagName("depositBalance").item(0).getTextContent();
                    if (eElement.getElementsByTagName("durationInDays").getLength() > 0)
                        duration = Integer.parseInt(eElement.getElementsByTagName("durationInDays").item(0).getTextContent());

                    if (!customerNum.isEmpty() && duration > 0 && isValidType(depositType) && (new BigDecimal(depositBalance).longValue() > 0)) {
                        Deposit newDeposit = new Deposit(customerNum,depositBalance,duration,depositType);
                        depositList.add(newDeposit);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isValidType(String type) {
        boolean result = ((type.equals("ShortTerm") || type.equals("LongTerm") || type.equals("Qarz")) ? true : false);
        return result;
    }

    public List<Deposit> getDepositList() {
        return depositList;
    }
}