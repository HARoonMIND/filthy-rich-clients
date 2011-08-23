import javax.sound.sampled.*;
import java.io.*;

public class PCMFilePlayerLeveler implements Runnable {
    File file;
    AudioInputStream in;
    SourceDataLine line;
    int frameSize;
    byte[] buffer; 
    Thread playThread;
    boolean playing;
    boolean notYetEOF;
    AudioFormat format;
    float level;

    final static float MAX_8_BITS_SIGNED = Byte.MAX_VALUE;
    final static float MAX_8_BITS_UNSIGNED = 0xff;
    final static float MAX_16_BITS_SIGNED = Short.MAX_VALUE;
    final static float MAX_16_BITS_UNSIGNED = 0xffff;
    
    public PCMFilePlayerLeveler (File f)
        throws IOException,
               UnsupportedAudioFileException,
               LineUnavailableException {
        file = f;
        // in = AudioSystem.getAudioInputStream (f);
        in = AudioSystem.getAudioInputStream (new BufferedInputStream (new FileInputStream(f)));
        format = in.getFormat();
        AudioFormat.Encoding formatEncoding = format.getEncoding();
        if (! (formatEncoding.equals (AudioFormat.Encoding.PCM_SIGNED) ||
               formatEncoding.equals (AudioFormat.Encoding.PCM_UNSIGNED)))
            throw new UnsupportedAudioFileException (
                                                     file.getName() + " is not PCM audio");
        System.out.println ("got PCM format:  " +
                            format.getChannels() + " channels, " + 
                            format.getSampleSizeInBits() + " bit samples");
        frameSize = format.getFrameSize();
        System.out.println ("got frame size: ");
        DataLine.Info info =
            new DataLine.Info (SourceDataLine.class, format);
        System.out.println ("got info");
        line = (SourceDataLine) AudioSystem.getLine (info);

        // figure out a small buffer size
        int bytesPerSec = format.getSampleSizeInBits() *
                          (int) format.getSampleRate();
        System.out.println ("bytesPerSec = " + bytesPerSec);
        int bufferSize = bytesPerSec / 20;
        buffer = new byte[bufferSize];

        System.out.println ("got line");
        line.open();
        System.out.println ("opened line");
        playThread = new Thread (this);
        playing = false;
        notYetEOF = true;
        playThread.start();
    }
    
    public void run() {
        int readPoint = 0;
        int bytesRead = 0;
        
        try {
            while (notYetEOF) {
                if (playing) {
                    // only write if the line will take at
                    // least a buffer-ful of data
                    if (line.available() < buffer.length) {
                        Thread.yield();
                        continue;
                    }
                    bytesRead = in.read (buffer,
                                         readPoint,
                                         buffer.length - readPoint);
                    if (bytesRead == -1) {
                        notYetEOF = false;
                        break;
                    }
                    // how many frames did we get,
                    // and how many are left over?
                    int frames = bytesRead / frameSize;
                    int leftover = bytesRead % frameSize;
                    // calculate level
                    calculateLevel (buffer, readPoint, leftover);
                    // if (level > 1)
                    //     System.out.println ("WTF? level = " + level);
                    // System.out.println ("level: " + level);
                    // send to line
                    line.write (buffer, readPoint, bytesRead-leftover);
                    // save the leftover bytes
                    System.arraycopy (buffer, bytesRead,
                                      buffer, 0,
                                      leftover);
                    readPoint = leftover;
                    
                } else {
                    // if not playing
                    // Thread.yield();
                    try { Thread.sleep (10);} 
                    catch (InterruptedException ie) {}
                }
            } // while notYetEOF
            System.out.println ("reached eof");
            line.drain();
            line.stop();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            // line.close();
        }
    } // run
    
    /** resets level by finding max value in buffer, taking
        into account whether these are 8 or 16 bit values
        (doesn't care about mono vs stereo - if one channel
        is disproportionately louder than the other, it wins)
     */
    private void calculateLevel (byte[] buffer,
                                 int readPoint,
                                 int leftOver) {
        int max = 0;
        boolean use16Bit = (format.getSampleSizeInBits() == 16);
        boolean signed = (format.getEncoding() == 
                          AudioFormat.Encoding.PCM_SIGNED);
        boolean bigEndian = (format.isBigEndian());
        if (use16Bit) {
            for (int i=readPoint; i<buffer.length-leftOver; i+=2) {
                int value = 0;
                // deal with endianness
                int hiByte = (bigEndian ? buffer[i] : buffer[i+1]);
                int loByte = (bigEndian ? buffer[i+1] : buffer [i]);
                if (signed) {
                    short shortVal = (short) hiByte;
                    shortVal = (short) ((shortVal << 8) | (byte) loByte);
                    value = shortVal;
                } else {
                    value = (hiByte << 8) | loByte;
                } 
                max = Math.max (max, value);
            } // for
        } else {
            // 8 bit - no endianness issues, just sign
            for (int i=readPoint; i<buffer.length-leftOver; i++) {
                int value = 0;
                if (signed) {
                    value = buffer [i];
                } else {
                    short shortVal = 0;
                    shortVal = (short) (shortVal | buffer [i]);
                    value = shortVal;
                }
                max = Math.max (max, value);
            } // for
        } // 8 bit
        // express max as float of 0.0 to 1.0 of max value
        // of 8 or 16 bits (signed or unsigned)
        if (signed) {
            if (use16Bit) { level = (float) max / MAX_16_BITS_SIGNED; }
            else { level = (float) max / MAX_8_BITS_SIGNED; }
        } else {
            if (use16Bit) { level = (float) max / MAX_16_BITS_UNSIGNED; }
            else { level = (float) max / MAX_8_BITS_UNSIGNED; }
        }
    } // calculateLevel
        



    
    public void start() {
        playing = true;
        if (! playThread.isAlive())
            playThread.start();
        line.start();
    }
    
    public void stop() {
        playing = false;
        line.stop();
    }
    
    public SourceDataLine getLine() {
        return line;
    }

    public File getFile() {
        return file;
    }

    public float getLevel() {
        return level;
    }

}


