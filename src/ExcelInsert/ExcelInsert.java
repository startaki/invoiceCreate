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

		// ���� �ε�
		Workbook workbook = WorkbookFactory.create(inputStream);
		// ��Ʈ �ε� 0, ù��° ��Ʈ �ε�
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowItr = sheet.iterator();
		// �ุŭ �ݺ�
		while (rowItr.hasNext()) {
			InvoiceDB ivdb = new InvoiceDB();
			Row row = rowItr.next();
			// ù ��° ���� ����� ��� ��ŵ, 2��° ����� data �ε�
			if (row.getRowNum() == 0) {
				continue;
			}
			Iterator<Cell> cellItr = row.cellIterator();
			// �� ���� �ѿ� �� �б� (�� �б�)
			while (cellItr.hasNext()) {
				Cell cell = cellItr.next();
				int index = cell.getColumnIndex();

				switch (index) {
				case 0: // �������� ���̵�
					ivdb.setSend_name(String.valueOf(cell));
					break;
				case 1: // ������ �� �̸�
					ivdb.setSend_phone(String.valueOf(cell));
					break;
				case 2: // ���� ǰ��
					ivdb.setSend_item(String.valueOf(cell));
					break;
				case 3: // ������ �� �����ȣ
					ivdb.setSend_postnum(String.valueOf(((Double)getValueFromCell(cell)).intValue()));
					break;
				case 4: // ������ �� �ּ�
					ivdb.setSend_addr(String.valueOf(cell));
					break;
				case 5: // ������ �� ���ּ�
					ivdb.setSend_addr_detail(String.valueOf(cell));
					break;
				case 6: // �޴� �� �̸�
					ivdb.setReci_name(String.valueOf(cell));
					break;
				case 7: // �޴� �� ����ó1
					ivdb.setReci_phone1(String.valueOf(cell));
					break;
				case 8: // �޴� �� ����ó2
					ivdb.setReci_phone2(String.valueOf(cell));
					break;
				case 9: // �޴� �� �����ȣ
					ivdb.setReci_postnum(String.valueOf(((Double)getValueFromCell(cell)).intValue()));
					break;
				case 10: // �޴� �� �ּ�
					ivdb.setReci_addr(String.valueOf(cell));
					break;
				case 11: // �޴� �� ���ּ�
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
				JOptionPane.showMessageDialog(null, dialogfont + i + "��° ����� ������ Ȯ���ϰ� �ٽ� �Է��ϼ���.");
				invoicesave = false;
				i = 0;
				break;
			}
		}
		if(invoicesave == true) {
			i = 0;
			JOptionPane.showMessageDialog(null, dialogfont + "����� �Է��� �Ϸ�Ǿ����ϴ�.");
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
