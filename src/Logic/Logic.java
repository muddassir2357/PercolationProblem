package Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Logic {
	List<Block> blocks;
	int gridSize;

	public List<Block> initializeGrid(int n) {
		blocks = new ArrayList<Block>();
		gridSize = n;
		for (int i = 0; i < (n * n); i++) {
			Block block = new Block();
			block.setId(i);
			blocks.add(block);
		}
		return blocks;

	}

	public int depth(int n) {
		int depth = 0;

		while (blocks.get(n).getId() != n) {
			n = blocks.get(n).getId();
			depth++;
		}
		return depth;

	}

	public boolean quickUnion(int n1, int n2) {

		if (blocks.get(n1).getId() == blocks.get(n2).getId())
			return false;
		else {
			int depth1 = depth(n1);

			int depth2 = depth(n2);
			//if (depth1 == depth2) {
				if (getParent(n1) < getParent(n2)) {
					while (blocks.get(n1).getId() != n1) {
						n1 = blocks.get(n1).getId(); // searching for parent
						if (n1 == n2)
							return false;// already connected and in same
											// hierarchy
					}
					while (blocks.get(n2).getId() != n2) {
						int temp = blocks.get(n2).getId();
						blocks.get(n2).setId(n1);
						n2 = temp;
					}
					blocks.get(n2).setId(n1);
					return true;

				} else {
					while (blocks.get(n2).getId() != n2) {
						n2 = blocks.get(n2).getId(); // searching for parent
						if (n2 == n1)
							return false;// already connected and in same
											// hierarchy
					}

					while (blocks.get(n1).getId() != n1) {
						int temp = blocks.get(n1).getId();
						blocks.get(n1).setId(n2);
						n1 = temp;
					}
					blocks.get(n1).setId(n2);
					return true;
				}
			

			/*else if ((depth2 < depth1) && depth2 != 0) {
				// depth=0 means its a single node, will obviously merge with
				// tree1
				// weighted union; tree having shorter depth will merge with
				// longer tree
				while (blocks.get(n2).getId() != n2) {
					n2 = blocks.get(n2).getId(); // searching for parent
					if (n2 == n1)
						return false;// already connected and in same hierarchy
				}

				while (blocks.get(n1).getId() != n1) {
					int temp = blocks.get(n1).getId();
					blocks.get(n1).setId(n2);
					n1 = temp;
				}
				blocks.get(n1).setId(n2);
				return true;

			} else if ((depth1 < depth2) && depth1 != 0) {
				while (blocks.get(n1).getId() != n1) {
					n1 = blocks.get(n1).getId(); // searching for parent
					if (n1 == n2)
						return false;// already connected and in same hierarchy
				}
				while (blocks.get(n2).getId() != n2) {
					int temp = blocks.get(n2).getId();
					blocks.get(n2).setId(n1);
					n2 = temp;
				}
				blocks.get(n2).setId(n1);
				return true;
			} else {
				System.out.println("Unable to make union");
				return false;
			}*/
		}

	}

	public int getParent(int n) {
		while (blocks.get(n).getId() != n)
			n = blocks.get(n).getId();
		return n;

	}

	public boolean isConnected(int n1, int n2) {

		while (blocks.get(n1).getId() != n1) {
			n1 = blocks.get(n1).getId();

		}
		while (blocks.get(n2).getId() != n2) {
			n2 = blocks.get(n2).getId();

		}
		if (n1 == n2)
			return true;
		else
			return false;
	}

	public boolean isConnected(Block n1, Block n2) {
		return n1.getId() == n2.getId();
	}

	public void fillBlocks(float p) {

		int p_inv = (int) ((1 / p) * 100);// converts the
											// probability to
											// fraction like 0.7 to
											// 100/133 where
											// p_inv=133
		for (int i = 0; i < blocks.size(); i++) {
			if (new Random().nextInt(p_inv) < 100)
				blocks.get(i).setOpen(true);
			else
				blocks.get(i).setOpen(false);

		}
	}

	public boolean isPercolating() {

		Block first = new Block();
		// first.setId(-1);

		Block last = new Block();
		last.setId(blocks.size());

		boolean temp = false;

		for (int i = 0; i < blocks.size(); i++) {

			if (blocks.get(i).isOpen()) {
				if (i < gridSize) {// initializing first node and connecting
									// first node
					if (!temp) {
						first.setId(blocks.get(i).getId());
					}

					quickUnion(first.getId(), i);
					//System.out.println("Union of " + first.getId() + "and " + i);
					temp = true;

				}
				if ((i + 1) % gridSize != 0) {// connecting node to right of
												// current node
					if (blocks.get(i + 1).isOpen()) {
						quickUnion(i, i + 1);
						//System.out.println("Union of " + first.getId() + "and " + (i + 1));
					}
				}
				if (i < ((gridSize * gridSize) - gridSize)) {// connecting
																// bottom node
					if (blocks.get(i + gridSize).isOpen()) {
						quickUnion(i, i + gridSize);
						//System.out.println("Union of " + first.getId() + "and " + (i + gridSize));
					}
				}
				if (i >= ((gridSize * gridSize) - gridSize)) {// setting up last
																// node
					// quickUnion(i, last.getId());
					if (last.getId() > blocks.get(i).getId()) {
						last.setId(blocks.get(i).getId());
						//System.out.println("Last Id set to " + last.getId());
					}

				}

			}
			if (i >= gridSize && !temp)
				return false;

		}

		return isConnected(first, last);

	}

	public void printBlocks() {

		for (int i = 0; i < blocks.size(); i++) {
			for (int j = 0; j < gridSize; j++) {
				if (blocks.get(i).isOpen())
					System.out.print("T  ");
				else
					System.out.print("F  ");
				if (j != gridSize - 1)
					i++;
			}
			System.out.println("");
		}
	}

}
