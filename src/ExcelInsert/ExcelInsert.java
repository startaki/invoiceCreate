package ExcelInsert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import Frame.Invoice;
import Frame.Login;
import InvoiceDB.InvoiceDAO;
import InvoiceDB.InvoiceDB;

public class ExcelInsert {
	public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	public static List<InvoiceDB> invoiceList;
	public static InvoiceDAO dao = InvoiceDAO.getInstance();
	private static boolean invoicesave = true;
	private static String dialogfont = "<html><h1 style='font-family:Malgun Gothic; font-weight: normal; font-size: 11pt;'>";
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		invoiceList = getInvoiceList();

	}

	public static List<InvoiceDB> getInvoiceList() throws IOException {
		invoiceList = new ArrayList<InvoiceDB>();

		String filePath = Invoice.filePath + Invoice.fileName;
		// String filePath = "C:\\student.xls";
		InputStream inputStream = new FileInputStream(filePath);

		// 엑셀 로드
		Workbook workbook = WorkbookFactory.create(inputStream);
		// 시트 로드 0, 첫번째 시트 로드
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowItr = sheet.iterator();
		// 행만큼 반복
		while (rowItr.hasNext()) {
			InvoiceDB ivdb = new InvoiceDB();
			Row row = rowItr.next();
			// 첫 번째 행이 헤더인 경우 스킵, 2번째 행부터 data 로드
			if (row.getRowNum() == 0) {
				continue;
			}
			Iterator<Cell> cellItr = row.cellIterator();
			// 한 행이 한열 씩 읽기 (셀 읽기)
			while (cellItr.hasNext()) {
				Cell cell = cellItr.next();
				int index = cell.getColumnIndex();

				switch (index) {
				case 0: // 접속중인 아이디
					ivdb.setSend_name(String.valueOf(cell));
					break;
				case 1: // 보내는 분 이름
					ivdb.setSend_phone(String.valueOf(cell));
					break;
				case 2: // 보낼 품목
					ivdb.setSend_item(String.valueOf(cell));
					break;
				case 3: // 보내는 분 우편번호
					ivdb.setSend_postnum(String.valueOf(((Double)getValueFromCell(cell)).intValue()));
					break;
				case 4: // 보내는 분 주소
					ivdb.setSend_addr(String.valueOf(cell));
					break;
				case 5: // 보내는 분 상세주소
					ivdb.setSend_addr_detail(String.valueOf(cell));
					break;
				case 6: // 받는 분 이름
					ivdb.setReci_name(String.valueOf(cell));
					break;
				case 7: // 받는 분 연락처1
					ivdb.setReci_phone1(String.valueOf(cell));
					break;
				case 8: // 받는 분 연락처2
					ivdb.setReci_phone2(String.valueOf(cell));
					break;
				case 9: // 받는 분 우편번호
					ivdb.setReci_postnum(String.valueOf(((Double)getValueFromCell(cell)).intValue()));
					break;
				case 10: // 받는 분 주소
					ivdb.setReci_addr(String.valueOf(cell));
					break;
				case 11: // 받는 분 상세주소
					ivdb.setReci_addr_detail(String.valueOf(cell));
					break;
				}
			}
			invoiceList.add(ivdb);

		}
		int i = 0;
		for (InvoiceDB InvoiceDB : invoiceList) {
			InvoiceDB.setInvoice_num(Invoice.random());
			InvoiceDB.setMem_id(Login.id);
			InvoiceDB.setSend_name(InvoiceDB.getSend_name());
			InvoiceDB.setSend_phone(InvoiceDB.getSend_phone());
			InvoiceDB.setSend_item(InvoiceDB.getSend_item());
			InvoiceDB.setSend_postnum(InvoiceDB.getSend_postnum());
			InvoiceDB.setSend_addr(InvoiceDB.getSend_addr());
			InvoiceDB.setSend_addr_detail(InvoiceDB.getSend_addr_detail());

			InvoiceDB.setReci_name(InvoiceDB.getReci_name());
			InvoiceDB.setReci_phone1(InvoiceDB.getReci_phone1());
			InvoiceDB.setReci_phone2(InvoiceDB.getReci_phone2());
			InvoiceDB.setReci_postnum(InvoiceDB.getReci_postnum());
			InvoiceDB.setReci_addr(InvoiceDB.getReci_addr());
			InvoiceDB.setReci_addr_detail(InvoiceDB.getReci_addr_detail());
			
			int result = dao.invoice_save(InvoiceDB);
			if (result == 1) {
				Vector<Object> row = new Vector<>();
				row.addElement(Invoice.invoicenum);
				row.addElement(InvoiceDB.getSend_name());
				row.addElement(InvoiceDB.getReci_name());
				row.addElement(InvoiceDB.getReci_addr());
				row.addElement("X");
				Invoice.tableModel.addRow(row);
				invoicesave = true;
				i++;
			} else {
				JOptionPane.showMessageDialog(null, dialogfont + i + "번째 운송장 정보를 확인하고 다시 입력하세요.");
				invoicesave = false;
				i = 0;
				break;
			}
		}
		if(invoicesave == true) {
			i = 0;
			JOptionPane.showMessageDialog(null, dialogfont + "운송장 입력이 완료되었습니다.");
		}
		return invoiceList;
	}
	
    private static Object getValueFromCell(Cell cell) {
        switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue();
        case BOOLEAN:
            return cell.getBooleanCellValue();
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            }
            return cell.getNumericCellValue();
        case FORMULA:
            return cell.getCellFormula();
        case BLANK:
            return "";
        default:
            return "";
        }
    }
}
