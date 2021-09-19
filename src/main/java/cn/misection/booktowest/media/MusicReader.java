package cn.misection.booktowest.media;

/**
 * @author javaman
 */
public class MusicReader {

    private static final MusicPlayer background = new MusicPlayer("sources/BGM");

    private static final MusicPlayer music = new MusicPlayer("sources/music");

    public static void readBgm(String s) {
        background.play(s);
    }

    public static void readMusic(String s) {
        music.playMusic(s);
    }

    /**
     * 关闭背景音乐;
     */
    public static void closeBgm() {
        MusicPlayer.CAN_PLAY_BGM = MusicPlayer.NO;
    }

    public static void openBgm() {
        background.play(background.getCurrentPlayingBGM());
        MusicPlayer.CAN_PLAY_BGM = MusicPlayer.YES;
    }

    /**
     * 关闭音效;
     */
    public static void closeMusic() {
        MusicPlayer.CAN_PLAY_MUSIC = MusicPlayer.NO;
    }

    public static void openMusic() {
        MusicPlayer.CAN_PLAY_MUSIC = MusicPlayer.YES;
    }
}
