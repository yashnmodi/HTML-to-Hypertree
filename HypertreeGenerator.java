import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

class HypertreeGenerator {
	public static void main(String args[]) throws Exception
	{
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Scanner sc = new Scanner(new File(args[0]));

		StringBuilder file = new StringBuilder();

		while (sc.hasNextLine())  {
			file.append(sc.nextLine());
		}

		ByteArrayInputStream input =  new ByteArrayInputStream(file.toString().replaceAll(">\\s*<", "><").getBytes("UTF-8"));
		
		Document document = builder.parse(input);

		Element root = document.getDocumentElement();

		//System.out.println("-----------------------------");

		Hypertree tree = new Hypertree(root, 0);

		if (args.length > 1) {
			String operation = args[1];
			int param = 1;
			if (args.length == 3) {
				param = Integer.parseInt(args[2]);
			}

			if (operation.equals("prime")) tree.prime();
			if (operation.equals("head")) tree.head(param);
			if (operation.equals("tail")) tree.tail(param);
			if (operation.equals("simpleTree")) tree.simpleTree(param);
		}

		tree.print();

		//System.out.println("\n-----------------------------");
	}
}