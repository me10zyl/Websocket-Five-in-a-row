package drawNGuess;

import java.util.Random;

public class GameUtil {
	public static String[] gameTitles = { "�׸�", "����", "������", "�ͽ�", "�ֿ�", "CD", "����", "������Ͽ", "����©��", "���", "���ݼ�", "����", "��ͷ��", "��", "����", "��ɳ��", "��", "����", "�ɵ�", "����", "�ɻ�", "��ɻ�", "��", "����", "��ϥ", "����", "����", "���ֹ�", "����", "����", "��ʦ", "���֤", "�ѻ���ǹ", "�ո��", "KTV", "�����", "��ү��", "����", "��ص�", "�޻�", "ĸ��", "NBA", "�ڿ�", "ţ����", "ţ���", "ţ����", "�Ų�", "��ʼ�ʱ���ٸ", "ȫ��Ͱ", "ɳɮ", "ʥ��", "����", "ʵ����", "ʨ����", "����Ա", "����", "����", "ˮ��", "����", "����", "��ַ", "�ʳȶ�", "�ʻ�", "С����", "����", "�̶�", "���ݳ���", "�³�", "ҽ��", "����", "����", "��", "������", "��Ͳ", "ֽ��", "���" };

	public static String getRandomTitle() {
		Random ran = new Random();
		return gameTitles[ran.nextInt(gameTitles.length)];
	}
}
