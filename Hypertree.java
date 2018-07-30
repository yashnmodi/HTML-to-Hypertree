import org.w3c.dom.*;

class Hypertree {

	Node root;
	int indent;

	Hypertree(Node root, int indent) {
		this.root = root;
		this.indent = indent;
	}

	public Hypertree prime()
	{
		NodeList children = root.getChildNodes();
		Node first = children.item(0);

		this.root = first;

		return this;
	}

	public Hypertree head(int n)
	{
		NodeList children = root.getChildNodes();

		int i = children.getLength();
		while (i > n) {
			root.removeChild(children.item(children.getLength()-1));
			i = children.getLength();
		}

		return this;
	}

	public Hypertree tail(int n)
	{
		NodeList children = root.getChildNodes();

		int i = children.getLength();
		while (i > n) {
			root.removeChild(children.item(0));
			i = children.getLength();
		}
		
		return this;
	}

	public Hypertree simpleTree(int n)
	{
		head(n);
		tail(1);
		return this;
	}

	public void print()
	{
		printIndent(indent);

		System.out.print("[Tag: "+root.getNodeName());

		NamedNodeMap attributes = root.getAttributes();
		if (attributes != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				Node attribute = attributes.item(i);
				System.out.print(", "+attribute.getNodeName()+": "+attribute.getNodeValue());
			}	
		}

		NodeList children = root.getChildNodes();

		if (children.getLength() == 1) {
			Node child = children.item(0);
			if (child.getNodeType() == Node.TEXT_NODE) {
				String value = child.getNodeValue().replaceAll("[\n\r]", "");
				if (value.length() != 0) {
					System.out.print(", Value: "+value);
				}
			}
		} 

		System.out.print(" ]\n");

		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			short type = child.getNodeType();
			if (type == Node.ELEMENT_NODE) {
				Hypertree tree = new Hypertree(child, indent + 1);
				tree.print();
			}
		}
	}

	private void printIndent(int indent)
	{
		for (int i = 0; i < indent; i++) {
			System.out.print("    ");
		}
	}
}