package convert.json;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class ConvertFromDOCXToJSON {

	public static void main(String[] args) {
		ConvertFromDOCXToJSON instance = new ConvertFromDOCXToJSON();
		instance.run();
	}
	
	private void run() {
		try {
            File file = new File("c:\\My Stuffs\\java_develop\\file\\test.docx");
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            
            XWPFDocument document = new XWPFDocument(fis); 

            List<XWPFParagraph> paragraphs = document.getParagraphs();


            for (XWPFParagraph para : paragraphs) {
                System.out.println(para.getText());
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
