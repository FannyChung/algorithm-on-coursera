import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.theme.SubstanceBottleGreenTheme;
import org.jvnet.substance.watermark.SubstanceImageWatermark;

public class client extends Applet {
	static {
		try {
			// �������
			UIManager
					.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(true);
			// ��������
			SubstanceLookAndFeel
					.setCurrentTheme(new SubstanceBottleGreenTheme());
			// ���ð�ť���
			SubstanceLookAndFeel
					.setCurrentButtonShaper(new StandardButtonShaper());
			 // ����ˮӡ
			SubstanceLookAndFeel
					.setCurrentWatermark(new SubstanceImageWatermark("SEU.jpg"));
			// ���ñ߿�
			SubstanceLookAndFeel
					.setCurrentBorderPainter(new StandardBorderPainter());
			// ���ý�����Ⱦ
			SubstanceLookAndFeel
					.setCurrentGradientPainter(new StandardGradientPainter());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private JComboBox<String> jcb1, jcb2;
	private JTextArea tf_name;
	private File file;
	private BufferedReader br;
	private HashMap<String, String> map = new HashMap<String, String>();

	private String fileNames[] = { "buyer.txt", "cart.txt", "Item.txt",
			"merAndEva.txt", "order.txt" };

	private String msgBack;
	private JTextArea showText;

	private void initUI() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), 8888);// -------------����Connection
																	// refused:
																	// connect
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		String itemNames[] = { "�û�", "���ﳵ", "��Ʒ", "�̼�������", "����" };
		jcb1 = new JComboBox<String>();
		jcb2 = new JComboBox<String>();
		for (int i = 0; i < 5; i++) {// ������ļ�һһ��Ӧ
			jcb2.addItem(itemNames[i]);
		}
		setCombo(0);
		jcb2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				int i = jcb2.getSelectedIndex();
				setCombo(i);
			}

		});

		tf_name = new JTextArea(1, 80);
		tf_name.setFont(new Font("����", Font.BOLD, 28));
		tf_name.setOpaque(false);
		JScrollPane js = new JScrollPane(tf_name);
		JButton btn_ok = new JButton("ȷ��");

		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"�ף��½����������������Ŷ~��\n�ǲ��Ƿ����û�д�?�ټ����socket��\nq(�R���Qq)", "����",
					JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"�ף��½����������������Ŷ~��\n�ǲ��Ƿ����û�д�?�ټ����socket��\nq(�R���Qq)", "����",
					JOptionPane.ERROR_MESSAGE);
		}

		btn_ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				msgBack = "";
				try {
					String sql = tf_name.getText().toString();
					out.writeObject(sql);
					System.out.println("������");
					out.flush();

					// �����ַ���
					System.out.println("������");
					msgBack = (String) (in.readObject());
					showText.setText(msgBack);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("���ص�string����");
					JOptionPane.showMessageDialog(null,
							"�ף����ݿⷵ�ص�string������Ŷ~�������԰�~~o(^��^)o��", "����",
							JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("e.printStackTrace()");
					JOptionPane.showMessageDialog(null,
							"�ף������������ݿ����ӳ��ִ�����Ŷ~~�ټ���飬q(�R���Qq)", "����",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		showText = new JTextArea(12, 70);
		showText.setOpaque(false);
		showText.setFont(new Font("����", Font.BOLD, 28));
		JScrollPane showTextJS = new JScrollPane(showText);

		JPanel top = new JPanel();
		top.add(jcb2);
		top.add(jcb1);
		top.add(btn_ok);

		JPanel down = new JPanel();
		down.add(js);

		JPanel center = new JPanel();
		center.add(showTextJS);
		/*
		 * this.add(top); this.add(down); this.add(showText);
		 */
		/*
		 * GridLayout GL = new GridLayout(3,4); GL.setHgap(20); //���������ˮƽ�ʹ�ֱ�ı߾�
		 * GL.setVgap(20); this.setLayout(GL); //���ĸ������ķ���?!��this:��ǰapplet
		 */
		/*
		 * top.setBounds(0, 0, w, h/5); down.setBounds(0, h/5, w, h/5);
		 * center.setBounds(0, 2*h/5, w, 4*h/5); this.add(top); this.add(down);
		 * this.add(showText);
		 */

		// setSize(800, 800);
		setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		// this.add(down,BorderLayout.SOUTH);
		JPanel mid = new JPanel();
		mid.setLayout(new BorderLayout());
		mid.add(down, BorderLayout.NORTH);
		mid.add(center);
		this.add(mid);
		this.setVisible(true);

	}

	private void setCombo(int i) {
		file = new File(fileNames[i]);
		jcb1.removeAllItems();
		jcb1.repaint();
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"�ף��ļ�������Ŷ~��\n�ǲ��ǷŴ��˵ط����أ���\nq(�R���Qq)", "����",
					JOptionPane.ERROR_MESSAGE);
		}

		String s = null;
		try {
			while ((s = br.readLine()) != null) {
				String[] tmp = s.split("#");
				map.put(tmp[0], tmp[1]);// ---------------����ArrayIndexOutOfBoundsException,��Ϊsql.txt���Ϸ�
				jcb1.addItem(tmp[0]);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"�ף���������ļ��д����˰�~��\nһ��Ҫ��'����#���'�����ĸ�ʽŶ��\nq(�R���Qq)", "����",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		jcb1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				String str = (String) jcb1.getSelectedItem();
				tf_name.setText(map.get(str));
			}
		});
	}

	@Override
	public void destroy() {
		try {
			socket.close();
			System.out.print("over");
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.destroy();
	}

	public client() {
		initUI();
	}

	public static void main(String[] args) {

		new client();
	}

}
