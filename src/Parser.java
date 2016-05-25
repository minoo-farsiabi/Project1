import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.farsiabi on 5/23/2016.
 */
public class Parser {

    private List<Deposit> depositList = new ArrayList<Deposit>();

    Parser() throws ExceptionPool{
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

                    String customerNumber = null;
                    String depositType = null;
                    String depositBalance = null;
                    int duration = 0;

                    if (eElement.getElementsByTagName("customerNumber").getLength() > 0)
                        customerNumber = eElement.getElementsByTagName("customerNumber").item(0).getTextContent();
                    if (eElement.getElementsByTagName("depositType").getLength() > 0)
                        depositType = eElement.getElementsByTagName("depositType").item(0).getTextContent();
                    if (eElement.getElementsByTagName("depositBalance").getLength() > 0)
                        depositBalance = eElement.getElementsByTagName("depositBalance").item(0).getTextContent();
                    if (eElement.getElementsByTagName("durationInDays").getLength() > 0)
                        duration = Integer.parseInt(eElement.getElementsByTagName("durationInDays").item(0).getTextContent());

                    createDepositIfPossible(customerNumber,depositBalance,duration,depositType);
                }
            }
        } catch (ParserConfigurationException e) {
            throw new ExceptionPool("Parser Configuration Problem!");
        } catch (IOException e) {
            throw new ExceptionPool("IOException");
        } catch (SAXException e) {
            throw new ExceptionPool("SAXException");
        }
    }
    private void createDepositIfPossible (String customerNumber, String depositBalance, int duration, String depositType) throws ExceptionPool {
        if (customerNumber == null) {
            throw new ExceptionPool("Empty customer number!");
        }
        else if (duration <= 0) {
            throw new ExceptionPool("Duration is invalid!");
        }
        else if (new BigDecimal(depositBalance).longValue() <= 0) {
            throw new ExceptionPool("Balance is invalid!");
        }
        else if (isValidType(depositType)) {
            Deposit newDeposit = new Deposit(customerNumber,depositBalance,duration,depositType);
            depositList.add(newDeposit);
        }
    }
    public boolean isValidType(String type) throws ExceptionPool {
        Class depositTypeClasses;
        try {
            depositTypeClasses = Class.forName(type + "Deposit");
        } catch(ClassNotFoundException e) {
            throw new ExceptionPool("Deposit type is invalid!");
        } catch (NoClassDefFoundError e) {
            throw new ExceptionPool("Deposit type is invalid!");
        }
        return ((depositTypeClasses == null) ? false : true);
    }

    public List<Deposit> getDepositList() {
        return depositList;
    }
}