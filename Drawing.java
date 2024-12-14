import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing {
	public static void main(String[] args) {

		JFrame frame = new JFrame("Draw Window, press space to clear, press enter to print");
		Thing thing = new Thing();
		frame.add(thing);

		frame.pack();

		frame.setSize(400, 800);

		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		thing.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				thing.shouldColor();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
					thing.shouldNotColor();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
				
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					thing.clearColors();
				}
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					thing.printColors();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		Thread t2 = new Thread() {
			public void run() {

				while (true) {
					try {
						thing.updateMouse();
						thing.repaint();
						Thread.sleep(1000 / 60);
					} catch (Exception e) {

					}
				}
			}
		};
		t2.run();

	}

	public static class Thing extends JPanel {

		int[] colors;

		float mouseX;
		float mouseY;
		float overallW = 8f;
		float overallH = 16f;
		boolean doColor = false;

		public Thing() {
			super();
			mouseX = mouseY = 0;
			colors = new int[(int)overallW * (int)overallH];
		}
		
		public void clearColors() {
			this.colors = new int[(int)overallW * (int)overallH];
		}
		
		public void printColors() {
			System.out.println("\n\n\n\n");
			System.out.print("[1,");
			for (int i : colors) {
				System.out.print( + i + ",");
			}
			System.out.println("]\n\n\n");
		}

		public void color() {
			int w = this.getWidth();
			int h = this.getHeight();
			float x = mouseX / w * overallW;
			float y = mouseY / h * overallH;

			colors[(int) x + (int) y * (int)overallW] = 1;
		}

		public void updateMouse() {
			mouseX = (float) this.getMousePosition().getX();
			mouseY = (float) this.getMousePosition().getY();
		}
		
		public void shouldColor() {
			this.doColor = true;
		}
		
		public void shouldNotColor() {
			this.doColor = false;
		}

		public void paint(Graphics g) {
			super.paint(g);
			if(doColor) {
				this.color();
			}
			Graphics2D g2d = (Graphics2D) g;
			float dw = this.getWidth() / overallW;
			float dh = this.getHeight() / overallH;
			float y1 = 0;
			float y2 = (1) * dh;

			for (int y = 0; y < (int)overallH; y++) {
				float x1 = 0;
				float x2 = (1) * dw;
				for (int x = 0; x < (int) overallW; x++) {

					g.setColor(colors[x + y * (int)overallW] == 1 ? Color.white : Color.black);

					if (mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2) {
						g.setColor(colors[x + y * (int)overallW] == 1 ? Color.white : Color.gray);
					}

					g2d.fillRect((int) x1, (int) y1, (int) (x2 - x1), (int) (y2 - y1));

					x1 += dw;
					x2 += dw;
				}
				y1 += dh;
				y2 += dh;
			}
		}

	}
}
