import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class mainView {

	/**
	 * UI Constants
	 */
	static int margin_height = 15;
	static int margin_width = 5;
	static int textFieldWidth = 50;
	static int width = 4*(textFieldWidth+margin_width);

	private JFrame frame;
	private String path;
	
	static private List<JTextField> startTimes = new ArrayList<JTextField>();
	static private List<JTextField> endTimes = new ArrayList<JTextField>();
	static private List<JTextField> rates = new ArrayList<JTextField>();

	static Thread changingRateThread;
	
	OutputStream out;
	InputStream input;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainView window = new mainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/*
		 * Calculate height
		 */
		int height = 100+margin_height*startTimes.size();
		
		int heightStart = margin_height;
		
		frame = new JFrame();
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel startLabel = new JLabel("Start");
		JLabel endLabel = new JLabel("End");
		JLabel rateLabel = new JLabel("Rate");
		JButton addButton = new JButton("+");
		startLabel.setBounds(0, heightStart, textFieldWidth, margin_height);
		endLabel.setBounds(margin_width+textFieldWidth, heightStart, textFieldWidth, margin_height);
		rateLabel.setBounds(2*(margin_width+textFieldWidth), heightStart, textFieldWidth, margin_height);
		addButton.setBounds(3*(margin_width+textFieldWidth), heightStart, textFieldWidth, margin_height);

		frame.getContentPane().add(startLabel);
		frame.getContentPane().add(endLabel);
		frame.getContentPane().add(rateLabel);
		frame.getContentPane().add(addButton);

		heightStart +=margin_height;
		
		int heightIndex = heightStart;
		for (JTextField jTextField: startTimes) {
			jTextField.setBounds(0, heightIndex, textFieldWidth, margin_height);
			frame.getContentPane().add(jTextField);
			heightIndex += margin_height;
		}
		
		heightIndex = heightStart;
		for (JTextField jTextField: endTimes) {
			jTextField.setBounds(textFieldWidth+margin_width, heightIndex, textFieldWidth, margin_height);
			frame.getContentPane().add(jTextField);
			heightIndex += margin_height;
		}
//
		heightIndex = heightStart;
		for (JTextField jTextField: rates) {
			jTextField.setBounds(2*textFieldWidth+2*margin_width, heightIndex, textFieldWidth, margin_height);
			frame.getContentPane().add(jTextField);
			heightIndex += margin_height;
		}
		frame.setVisible(true);
		final mainView weakself = this;
		
		JButton browse = new JButton("Browse");
		browse.setBounds(0, heightIndex, 90, 20);

		JButton play = new JButton("Play");
		play.setBounds(100, heightIndex, 90, 20);

		browse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Create a file chooser
				final JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(frame);
				try {
					weakself.path = fc.getSelectedFile().getAbsolutePath();

				} catch (Exception eee) {
					// pathField.setText("");
				}

			}
		});
		
		frame.add(play);
		frame.add(browse);
		frame.setVisible(true);

		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] argumentsVideo = new String[] {"vlc", "-I rc", weakself.path};

				Process proc = null;
				try {
					proc = new ProcessBuilder(argumentsVideo).start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				weakself.input = proc.getInputStream();
				weakself.out = proc.getOutputStream();
				weakself.runThreadChangingRate();
			}
		});
		
		/**
		 * Button methods
		 */
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				startTimes.add(new JTextField());
				endTimes.add(new JTextField());
				rates.add(new JTextField());
				frame.setVisible(false);
				frame.dispose();
				weakself.initialize();
			}
		});
		
		
	}
	
	@SuppressWarnings("static-access")
	private void runThreadChangingRate() {
		if (this.changingRateThread != null) {
			this.changingRateThread.destroy();
		}
		
		final mainView weakSelf = this;
		
		this.changingRateThread = new Thread() {
		    public void run() {
		    	while(true) {
		    		try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
		    		int time = weakSelf.getTime();
		    		
		    		boolean flag = false;
		    		
		    		for (int i=0; i < startTimes.size(); i++) {
		    			String startTime = startTimes.get(i).getText();
		    			String endTime = endTimes.get(i).getText();
		    			String rateis = rates.get(i).getText();
		    			
		    			if(startTime == null || startTime =="" || endTime == null || endTime =="" || rateis == null || rateis =="" )
		    			{
		    				continue;
		    			}
		    			
		    			System.out.println("current time = " + time + " " + startTime + " " + endTime + " " + rateis);
		    			try {
		    				if (Integer.parseInt(startTime)
			    					<= time && Integer.parseInt(endTime) >= time) {
			    				weakSelf.setRate(Float.parseFloat(rateis));
			    				flag = true;
			    				break;
			    			}
		    			} catch (Exception e){
		    				continue;
		    			}
		    			
		    			
		    		}
		    		
		    		if(!flag) {
		    			weakSelf.setRate(1.0f);
		    		}
		    	}
		    }  
		};

		this.changingRateThread.start();
	}
	
	private int getTime() {
		byte[] timeBytes = new byte[9999];
		try {
			out.write("get_time\n".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			input.read(timeBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String time = new String(timeBytes);
		String[] times = time.split("\r\n");
		
		int res = 0;
		
		try {
		res = Integer.parseInt(times[0]);
		} catch (Exception e) {
			System.out.println("Time no good");
		}
		
		return res;
	}

	private void setRate(Float rate)
	{
		try {
			out.write(("rate " + rate + "\n").getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
