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
			// 设置外观
			UIManager
					.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(true);
			// 设置主题
			SubstanceLookAndFeel
					.setCurrentTheme(new SubstanceBottleGreenTheme());
			// 设置按钮外观
			SubstanceLookAndFeel
					.setCurrentButtonShaper(new StandardButtonShaper());
			 // 设置水印
			SubstanceLookAndFeel
					.setCurrentWatermark(new SubstanceImageWatermark("SEU.jpg"));
			// 设置边框
			SubstanceLookAndFeel
					.setCurrentBorderPainter(new StandardBorderPainter());
			// 设置渐变渲染
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
			socket = new Socket(InetAddress.getLocalHost(), 8888);// -------------出错：Connection
																	// refused:
																	// connect
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		String itemNames[] = { "用户", "购物车", "商品", "商家与评价", "订单" };
		jcb1 = new JComboBox<String>();
		jcb2 = new JComboBox<String>();
		for (int i = 0; i < 5; i++) {// 对五个文件一一对应
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
		tf_name.setFont(new Font("黑体", Font.BOLD, 28));
		tf_name.setOpaque(false);
		JScrollPane js = new JScrollPane(tf_name);
		JButton btn_ok = new JButton("确定");

		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"亲，新建输入输出流错误了哦~，\n是不是服务端没有打开?再检查检查socket。\nq(≧▽≦q)", "错误",
					JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"亲，新建输入输出流错误了哦~，\n是不是服务端没有打开?再检查检查socket。\nq(≧▽≦q)", "错误",
					JOptionPane.ERROR_MESSAGE);
		}

		btn_ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				msgBack = "";
				try {
					String sql = tf_name.getText().toString();
					out.writeObject(sql);
					System.out.println("发送中");
					out.flush();

					// 接受字符串
					System.out.println("接受中");
					msgBack = (String) (in.readObject());
					showText.setText(msgBack);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("返回的string错误");
					JOptionPane.showMessageDialog(null,
							"亲，数据库返回的string错误了哦~，请重试吧~~o(^▽^)o。", "错误",
							JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("e.printStackTrace()");
					JOptionPane.showMessageDialog(null,
							"亲，服务器、数据库连接出现错误了哦~~再检查检查，q(≧▽≦q)", "错误",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		showText = new JTextArea(12, 70);
		showText.setOpaque(false);
		showText.setFont(new Font("黑体", Font.BOLD, 28));
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
		 * GridLayout GL = new GridLayout(3,4); GL.setHgap(20); //设置组件的水平和垂直的边距
		 * GL.setVgap(20); this.setLayout(GL); //是哪个类对象的方法?!是this:当前applet
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
					"亲，文件不存在哦~，\n是不是放错了地方了呢？。\nq(≧▽≦q)", "错误",
					JOptionPane.ERROR_MESSAGE);
		}

		String s = null;
		try {
			while ((s = br.readLine()) != null) {
				String[] tmp = s.split("#");
				map.put(tmp[0], tmp[1]);// ---------------出错ArrayIndexOutOfBoundsException,因为sql.txt不合法
				jcb1.addItem(tmp[0]);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"亲，你的输入文件有错误了啊~，\n一定要是'动作#语句'这样的格式哦。\nq(≧▽≦q)", "错误",
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
