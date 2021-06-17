package postnumSearch;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParsing {

    // tag값의 정보를 가져오는 메소드
	public static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}

	public XmlParsing(String Search_word, DefaultTableModel tableModel) {
		int page = 1;	// 페이지 초기값 
		try{
			while(true){
				// parsing할 url 지정(API 키 포함해서)
				String url = "http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll?ServiceKey=BS4jm5ZcUyKXTxcLRp7gaFPgzEehYVloVuv0KFWX1aiWcbjCLPWpN3GGay1GiOaHOb32wXLPobRNt/va2MZINA==&countPerPage=50&currentPage=" + page + "&srchwrd=" + Search_word.replaceAll(" ", "%20");
				int pagenum = 0;
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);
				
				// root tag 
				doc.getDocumentElement().normalize();
				
				// 파싱할 tag
				NodeList nList = doc.getElementsByTagName("newAddressListAreaCdSearchAll");
				NodeList totalpage = doc.getElementsByTagName("cmmMsgHeader");
				
				for(int temp = 0; temp < totalpage.getLength(); temp++){
					Node nNode = totalpage.item(temp);
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
						Element eElement = (Element) nNode;
						pagenum = Integer.parseInt(XmlParsing.getTagValue("totalPage", eElement));
					}	
				}
				
				for(int temp = 0; temp < nList.getLength(); temp++){
					Node nNode = nList.item(temp);
					
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
						
						Element eElement = (Element) nNode;
						Vector<Object> row = new Vector<>();
						row.addElement(XmlParsing.getTagValue("zipNo", eElement));
						row.addElement(XmlParsing.getTagValue("lnmAdres", eElement));
						tableModel.addRow(row);
					}
				}
				
				page += 1;
				if (page - 1 == pagenum) {
					break;
				}
			}
			
		} catch (Exception ex){	
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
		
}