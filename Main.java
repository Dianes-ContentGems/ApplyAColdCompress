package ApplyaColdCompress;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Pattern;

class Main {

	public static void main(String[] args) {
		Pattern dd = Pattern.compile("[0-9][0-9]");
		Scanner scanner = new Scanner(System.in);

		Queue<Integer> tokens = new LinkedList<Integer>();
		String line;
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			for (int i = 0; i < line.length(); i += 2) {
				Integer c1 = Integer.parseInt("" + line.charAt(i));
				Integer c2 = Integer.parseInt("" + line.charAt(i + 1));
				tokens.add(c1);
				tokens.add(c2);
			}

			List<String> output = split(tokens);
			print(output);
		}
	}

	private static void print(List<String> output) {
		for (String string : output) {
			System.out.println(string);
		}
	}

	private static List<String> split(Queue<Integer> tokens) {
		int c0 = tokens.poll();
		int c1 = tokens.poll();
		// if the input is correct we won't look at null values
		if (c0 != c1) {
			if (c0 == 1) {
				return processHorizSplit(split(tokens), split(tokens));
			} else {
				return processVertSplit(split(tokens), split(tokens));
			}
		} else {
			if (c0 == 0) {
				return black();
			} else {
				return white();
			}
		}
	}

	private static List<String> processVertSplit(List<String> top,
			List<String> bottom) {
		int leftWidth = top.get(0).length();
		int rightWidth = bottom.get(0).length();
		int lcm = lcm(leftWidth, rightWidth);

		top = scaleVert(top, lcm);
		bottom = scaleVert(bottom, lcm);
		return concatVert(top, bottom);
	}

	private static List<String> processHorizSplit(List<String> left,
			List<String> right) {
		int leftHeight = left.size();
		int rightHeight = right.size();
		int lcm = lcm(leftHeight, rightHeight);

		left = scaleHoriz(left, lcm);
		right = scaleHoriz(right, lcm);
		return concatHoriz(left, right);
	}

	private static List<String> concatHoriz(List<String> left,
			List<String> right) {
		List<String> output = new LinkedList<String>();

		Iterator<String> lIt = left.iterator();
		Iterator<String> rIt = right.iterator();

		while (lIt.hasNext()) {
			output.add(lIt.next() + rIt.next());
		}
		return output;
	}

	private static List<String> concatVert(List<String> top, List<String> bottom) {
		List<String> output = new LinkedList<String>();

		Iterator<String> tIt = top.iterator();
		Iterator<String> bIt = bottom.iterator();

		while (tIt.hasNext()) {
			output.add(tIt.next());
		}
		while (bIt.hasNext()) {
			output.add(bIt.next());
		}
		return output;
	}

	private static List<String> scaleVert(List<String> img, int lcm) {
		int scaleFactor = lcm / img.size();
		List<String> output = new LinkedList<String>();
		for (int i = 0; i < lcm; i++) {
			output.add(img.get((int) (i / (double) scaleFactor)));
		}
		return output;
	}

	private static List<String> scaleHoriz(List<String> img, int lcm) {
		int scaleFactor = lcm / img.get(0).length();
		List<String> output = new LinkedList<String>();
		for (int j = 0; j < img.size(); j++) {
			String str = "";
			for (int i = 0; i < lcm; i++) {
				str += img.get(j).charAt(
						(int) (i / (double) scaleFactor));
			}
			output.add(str);
		}
		return output;
	}

	private static List<String> black() {
		LinkedList<String> tmp = new LinkedList<String>();
		tmp.add("X");
		return tmp;
	}

	private static List<String> white() {
		LinkedList<String> tmp = new LinkedList<String>();
		tmp.add(" ");
		return tmp;
	}

	private static int lcm(int l, int r) {
		return (int) (l * r / gcd(l, r));
	}

	private static long gcd(int l, int r) {
		if (l == r) {
			return l;
		} else if (l > r) {
			return gcd(l - r, r);
		} else {
			return gcd(r, r - l);
		}
	}
}
