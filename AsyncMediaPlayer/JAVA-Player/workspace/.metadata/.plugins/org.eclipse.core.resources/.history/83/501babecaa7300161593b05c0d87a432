package editor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MaskCreator {

	static JEvalWrapper jeval = new JEvalWrapper();
	float videoSecondsCounter = 0;
	float audioSecondsCounter = 0;
	List<data> points;

	public enum Command {
		opWait, opRate, opPause, opPlay, opSeek
	}

	public class Operation {
		public Command op;
		public float num;

		public Operation(Command o, float n) {
			op = o;
			num = n;
		}

		public String getOpString() {
			switch (op) {
			case opWait:
				return "sleep";
			case opRate:
				return "rate";
			case opPause:
				// return "rate"; // instead of pausing, we do rate 100
				return "pause";
			case opPlay:
//				return "rate";
				 return "play";
			case opSeek:
				return "seek";
			default:
				assert (false);
				return "Unsupported";
			}
		}
	}

	public List<Operation> audioMask = new ArrayList<Operation>();
	public List<Operation> videoMask = new ArrayList<Operation>();

	public MaskCreator(List<data> pList) {
		points = new ArrayList<data>(pList);
		doMaskFiles();
		printToFiles();
	}

	void printToFiles() {
		PrintWriter writerAudio = null;
		try {
			writerAudio = new PrintWriter("/home/vlc/simpleMaskAudio", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter writerVideo = null;
		try {
			writerVideo = new PrintWriter("/home/vlc/simpleMaskVideo", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// audio file
		for (Operation p : audioMask) {
			writerAudio.println(p.getOpString() + " " + p.num);
		}

		// video file
		for (Operation p : videoMask) {
			writerVideo.println(p.getOpString() + " " + p.num);
		}

		writerAudio.close();
		writerVideo.close();
	}

	void doMaskFiles() {
		Collections.sort(points);

		/**
		 * Not important, only needed for duration first time
		 */
		data headDummyData = new data();
		headDummyData.startInSeconds = 0;
		points.add(0, headDummyData);

		/** end of dummy point */

		/**
		 * these variables will keep tracking of duration of real movie
		 * according to audio and video
		 */
//		float audioInMovie = 0.0f;
//		float videoInMovie = 0.0f;
		/** end of tracking duration */
		
//		audioMask.add(new Operation(Command.opWait, pointDuration
//				/ soundRate));
		
		// TODO 1. add last point to be the last second in video
		// 2. add dummy point as first second (second 0).
		float distanceBetweenVideoAudio = 0.f; // distance between video and audio
		for (int i = 1; i < points.size(); i++) {
			data currentData = points.get(i);
			data.SyncType sType = currentData.type;
			float pointDuration = currentData.startInSeconds
					- points.get(i - 1).startInSeconds;
			float soundRate = jeval.getValue(currentData.audioFunc, 0.f);
			float videoRate = jeval.getValue(currentData.videoFunc, 0.f);
			distanceBetweenVideoAudio += pointDuration/soundRate - pointDuration/videoRate;
			switch (sType) {
			case SyncTypeDontWait:
				audioMask.add(new Operation(Command.opRate, soundRate));
				videoMask.add(new Operation(Command.opRate, videoRate));
				audioMask.add(new Operation(Command.opWait, pointDuration/ soundRate));
				videoMask.add(new Operation(Command.opWait, pointDuration/ videoRate));
				break;
			case SyncTypeWait:
				if(distanceBetweenVideoAudio > 0){
					videoMask.add(new Operation(Command.opRate, videoRate));
					videoMask.add(new Operation(Command.opWait, pointDuration/ videoRate));
					videoMask.add(new Operation(Command.opRate, 0.1f));
					videoMask.add(new Operation(Command.opWait, distanceBetweenVideoAudio/(soundRate - 0.1f)));
					videoMask.add(new Operation(Command.opRate, videoRate < soundRate ? videoRate : soundRate));
					audioMask.add(new Operation(Command.opRate, soundRate));
					audioMask.add(new Operation(Command.opWait, pointDuration/ soundRate));
					audioMask.add(new Operation(Command.opRate, videoRate < soundRate ? videoRate : soundRate));
				} else {
					audioMask.add(new Operation(Command.opRate, soundRate));
					audioMask.add(new Operation(Command.opWait, pointDuration/ soundRate));
					audioMask.add(new Operation(Command.opRate, 0.1f));
					audioMask.add(new Operation(Command.opWait, -distanceBetweenVideoAudio/(videoRate - 0.1f)));
					audioMask.add(new Operation(Command.opRate, videoRate < soundRate ? videoRate : soundRate));
					videoMask.add(new Operation(Command.opRate, videoRate));
					videoMask.add(new Operation(Command.opWait, pointDuration/ videoRate));
					videoMask.add(new Operation(Command.opRate, videoRate < soundRate ? videoRate : soundRate));
				}
				distanceBetweenVideoAudio = 0;
				break;
			case SyncTypeDT:
				audioMask.add(new Operation(Command.opRate, soundRate));
				videoMask.add(new Operation(Command.opRate, videoRate));
				audioMask.add(new Operation(Command.opWait, pointDuration/ soundRate));
				videoMask.add(new Operation(Command.opWait, pointDuration/ videoRate));
				float dt = currentData.dt;
				if(distanceBetweenVideoAudio > 0){
					float Vx = dt * videoRate / (dt - distanceBetweenVideoAudio * (videoRate/soundRate));
					audioMask.add(new Operation(Command.opRate, Vx));
					audioMask.add(new Operation(Command.opWait, dt/Vx));
					audioMask.add(new Operation(Command.opRate, videoRate));
					videoMask.add(new Operation(Command.opWait, dt/videoRate));

				} else {
					float Vx = dt * soundRate / (dt - distanceBetweenVideoAudio * (soundRate/videoRate));
					videoMask.add(new Operation(Command.opRate, Vx));
					videoMask.add(new Operation(Command.opWait, dt/Vx));
					videoMask.add(new Operation(Command.opRate, soundRate));
					audioMask.add(new Operation(Command.opWait, dt/soundRate));
				}
				points.get(i).startInSeconds += dt; 
				distanceBetweenVideoAudio = 0;
				break;
			default:
				assert (false);
			}

		}
	}
}
