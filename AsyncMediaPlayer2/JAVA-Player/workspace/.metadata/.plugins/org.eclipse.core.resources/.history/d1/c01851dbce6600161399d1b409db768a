package player;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import editor.JEvalWrapper;
import editor.data;
import editor.getIcons;
import editor.maskEditor;

public class player {

	final static String vlcPath = "/home/vlc/workspace/vlcProject/vlc";
	static JEvalWrapper jeval = new JEvalWrapper();

	public JFrame frame;
	public maskEditor maskeditor = null;
	private OutputStream outAudio;
	private OutputStream outVideo;
	private InputStream inputAudio;
	private InputStream inputVideo;

	private Process procVideo;
	private Process procAudio;

	private int length = 0;
	private String path;

	boolean runThreads = true;

	boolean reopen = true;

	int timeInAudio = 0;
	int timeInVideo = 0;
	
	float lastRate = 1;
	
	Thread updateTimeThreadVideo;
	Thread updateTimeThreadAudio;

	private Float globalRate = 1.0f;
	
	private List<data> rateFunctions = new ArrayList<data>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player window = new player("/home/vlc/Downloads/ss.avi");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public player(String path) throws IOException {
		this.path = path;
		openFiles();
		initialize();
	}
	//amawasi
	public player(String path, List<data> list) throws IOException {
		this.rateFunctions = list;
		this.path = path;
		openFiles();
		initialize();
	}
	

	private void openFiles() throws IOException {
		if (reopen == false) {
			return;
		}

		reopen = false;

		String[] argumentsVideo = new String[] { vlcPath, "-I rc", path,
				"--no-video" };
		String[] arguementsAudio = new String[] { vlcPath, "-I rc", path,
				"--no-audio" };

		ProcessBuilder processVideo = new ProcessBuilder(argumentsVideo);
		ProcessBuilder processAudio = new ProcessBuilder(arguementsAudio);

		procVideo = processVideo.start();
		procAudio = processAudio.start();

		inputVideo = procVideo.getInputStream();
		outVideo = procVideo.getOutputStream();

		inputAudio = procAudio.getInputStream();
		outAudio = procAudio.getOutputStream();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		final int margenBetweenButtons = 0;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);

		ImageIcon buttonIcon;
		int currentX = 0;
		int currentY = 0;

		// play button
		buttonIcon = getIcons.getPlayButton();

		JButton btnPlayButton = new JButton(getIcons.getPlayButton());
		btnPlayButton.setBorder(BorderFactory.createEmptyBorder());
		btnPlayButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					openFiles();

					outAudio.write("play\n".getBytes());
					outVideo.write("play\n".getBytes());

					outVideo.flush();
					outAudio.flush();
				} catch (Exception e1) {
					// @TODO
				}
			}
		});
		btnPlayButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(btnPlayButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;
		// pause button
		buttonIcon = getIcons.getPauseButton();

		JButton btnPauseButton = new JButton(buttonIcon);
		btnPauseButton.setBorder(BorderFactory.createEmptyBorder());
		btnPauseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					outVideo.write("pause\n".getBytes());
					outAudio.write("pause\n".getBytes());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					outAudio.flush();
					outVideo.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPauseButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(btnPauseButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;

		// stop button
		buttonIcon = getIcons.getStopButton();

		JButton btnStopButton = new JButton(buttonIcon);
		btnStopButton.setBorder(BorderFactory.createEmptyBorder());
		btnStopButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					outVideo.write("stop\n".getBytes());
					outAudio.write("stop\n".getBytes());
					reopen = true;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					outAudio.flush();
					outVideo.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnStopButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(btnStopButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;
		currentY += buttonIcon.getIconWidth() + margenBetweenButtons + 10;

		// rate - change
		final JLabel rate = new JLabel();

		currentY += margenBetweenButtons;

		rate.setBounds(0, currentY, currentX - margenBetweenButtons + 10, 10);
		rate.setText("Global rate: 1.0");

		currentX += 10;
		frame.getContentPane().add(rate);

		currentY += 20;

		JButton rateUp = new JButton("up");
		rateUp.setBounds(0, currentY, 80, 30);
		currentX += 10;

		final player weekSelf = this;

		rateUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int currentTime = 0;
//				String currentAudioFucntion = null;
//				String currentVideoFunction = null;
//				for(int i = 0 ; i < rateFunctions.size() ; i++){
//					if(currentTime >= rateFunctions.get(i).startInSeconds){
//						currentAudioFucntion = rateFunctions.get(i).audioFunc;
//						currentVideoFunction = rateFunctions.get(i).videoFunc;
//					}
//				}
//				if(currentAudioFucntion == null || currentVideoFunction == null){
//					assert(false);
//				}
				
				
				
				weekSelf.globalRate = globalRate + 0.1f < 2.0f ? globalRate + 0.1f
						: 2.0f;
				rate.setText("Global Rate: " + weekSelf.globalRate.toString());
				
//				weekSelf.changeRate();
			}
		});

		JButton rateDown = new JButton("down");
		rateDown.setBounds(100, currentY, 80, 30);
		currentX += 10;
		rateDown.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				weekSelf.globalRate = globalRate - 0.1f > 0.5f ? globalRate - 0.1f
						: 0.5f;
				rate.setText("Global Rate: " + weekSelf.globalRate.toString());
				
//				weekSelf.changeRate();
			}
		});

		frame.getContentPane().add(rateUp);
		frame.getContentPane().add(rateDown);

		currentY += 36;

		// update time of video
		final JLabel timeVideo = new JLabel();

		currentY += margenBetweenButtons;

		timeVideo.setBounds(0, currentY, currentX - margenBetweenButtons + 10,
				10);
		timeVideo.setText("Time video.");

		currentY += 10;

		updateTimeThreadVideo = new Thread() {
			@Override
			public void run() {

				while (runThreads) {
					try {
						while (reopen)
							;
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					}
					try {
						timeInVideo = getTime().getFirst();
						timeVideo.setText("Video Time: "
								+ timeInVideo);
						if (length == 0) {
							length = getLength().getSecond();
						}
						changeRate();
					} catch (Exception e) {

					}
				}
			};
		};

		// update time of audio
		final JLabel timeAudio = new JLabel();

		currentY += margenBetweenButtons;

		timeAudio.setBounds(0, currentY, currentX - margenBetweenButtons + 10,
				10);
		timeAudio.setText("Time audio.");

		currentY += 10;

		updateTimeThreadAudio = new Thread() {
			@Override
			public void run() {

				while (runThreads) {
					try {
						while (reopen)
							;
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					}
					try {
						timeInAudio = getTime().getSecond();

						timeAudio.setText("Audio Time: "
								+ timeInAudio);
						if (length == 0) {
							length = getLength().getFirst();
						}
					} catch (Exception e) {

					}
				}
			};
		};

		updateTimeThreadVideo.start();
		updateTimeThreadAudio.start();

		frame.getContentPane().add(timeAudio);
		frame.getContentPane().add(timeVideo);

		// update frame
		frame.setBounds(100, 100, currentX - margenBetweenButtons + 10,
				currentY + 30);

		final player weakSelf = this;

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (weakSelf.maskeditor != null) {
					weakSelf.maskeditor.reOpen();
				}

				try {
					weakSelf.finalize();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					System.exit(1);
				}
			}
		});

	}

	private Pair<Integer, Integer> getLength() {
		try {
			byte[] timeBytesAudio = new byte[9999];
			byte[] timeBytesVideo = new byte[9999];

			outAudio.write("get_length\n".getBytes());
			outVideo.write("get_length\n".getBytes());

			outAudio.flush();
			outVideo.flush();

			inputAudio.read(timeBytesAudio);
			inputVideo.read(timeBytesVideo);

			String timeAudio = new String(timeBytesAudio);
			String timeVideo = new String(timeBytesAudio);

			String[] timesAudio = timeAudio.split("\r\n");
			String[] timesVideo = timeVideo.split("\r\n");

			System.out.println("Lenght is audio "
					+ Integer.parseInt(timesAudio[0]));
			System.out.println("Lenght is video "
					+ Integer.parseInt(timesVideo[0]));

			return new Pair<Integer, Integer>(Integer.parseInt(timesAudio[0]),
					Integer.parseInt(timesVideo[0]));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Pair<Integer, Integer>(-1, -1);

	}
	
	private void changeRate() {
		int minTime = Math.min(timeInAudio, timeInVideo);
		
		String func = "x";
		
		for (data d: this.rateFunctions) {
			if (minTime <= d.startInSeconds) {
				func = d.userRateFunction;
				break;
			}
		}
		
		if (func == null) {
			System.out.println("func null!!!! =");
			func = "x";
		}
		
		System.out.println("minTime =" + minTime +". func =" +func);
		
		float rate = jeval.getValue(func, this.globalRate);

		System.out.println("rate =" + rate +". lastRate =" +this.lastRate);

		
		if (rate == this.lastRate) {
			return;
		}
		
		this.lastRate = rate;
		this.setRate(rate);
	}

	private Pair<Integer, Integer> getTime() {
		try {
			byte[] timeBytesAudio = new byte[9999];
			byte[] timeBytesVideo = new byte[9999];

			outAudio.write("get_time\n".getBytes());
			outVideo.write("get_time\n".getBytes());

			try {
				outAudio.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			outVideo.flush();

			inputAudio.read(timeBytesAudio);
			inputVideo.read(timeBytesVideo);

			String timeAudio = new String(timeBytesAudio);
			String timeVideo = new String(timeBytesVideo);

			String[] timesAudio = timeAudio.split("\r\n");
			String[] timesVideo = timeVideo.split("\r\n");

			System.out.println("Lenght is audio "
					+ Integer.parseInt(timesAudio[0]));
			System.out.println("Lenght is video "
					+ Integer.parseInt(timesVideo[0]));

			return new Pair<Integer, Integer>(Integer.parseInt(timesVideo[0]),
					Integer.parseInt(timesAudio[0]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void finalize() throws Throwable {
		procAudio.destroy();
		procVideo.destroy();
		runThreads = false;
		frame.dispose();

		super.finalize();
	}

	private void setRate(Float rate)
	{
		try {
			outAudio.write(("rate " + rate + "\n").getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			outVideo.write(("rate " + rate + "\n").getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			outAudio.flush();
			outVideo.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
