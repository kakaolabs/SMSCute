package com.kakaolabs.smscute.util;

public class StringUtil {
	private static final Character[] coDauA = { 'à', 'á', 'ạ', 'ả', 'ã', 'â',
			'ầ', 'ấ', 'ậ', 'ẩ', 'ẫ', 'ă', 'ằ', 'ắ', 'ặ', 'ẳ', 'ẵ', 'À', 'Á',
			'Ạ', 'Ả', 'Ã', 'Â', 'Ầ', 'Ấ', 'Ậ', 'Ẩ', 'Ẫ', 'Ă', 'Ằ', 'Ắ', 'Ặ',
			'Ẳ', 'Ẵ' };
	private static final Character[] coDauE = { 'è', 'é', 'ẹ', 'ẻ', 'ẽ', 'ê',
			'ề', 'ế', 'ệ', 'ể', 'ễ', 'È', 'É', 'Ẹ', 'Ẻ', 'Ẽ', 'Ê', 'Ề', 'Ế',
			'Ệ', 'Ể', 'Ễ' };
	private static final Character[] coDauI = { 'ì', 'í', 'ị', 'ỉ', 'ĩ', 'Ì',
			'Í', 'Ị', 'Ỉ', 'Ĩ' };
	private static final Character[] coDauO = { 'ò', 'ó', 'ọ', 'ỏ', 'õ', 'ô',
			'ồ', 'ố', 'ộ', 'ổ', 'ỗ', 'ơ', 'ờ', 'ớ', 'ợ', 'ở', 'ỡ', 'Ò', 'Ó',
			'Ọ', 'Ỏ', 'Õ', 'Ô', 'Ồ', 'Ố', 'Ộ', 'Ổ', 'Ỗ', 'Ơ', 'Ờ', 'Ớ', 'Ợ',
			'Ở', 'Ỡ' };
	private static final Character[] coDauU = { 'ù', 'ú', 'ụ', 'ủ', 'ũ', 'ư',
			'ừ', 'ứ', 'ự', 'ử', 'ữ', 'Ù', 'Ú', 'Ụ', 'Ủ', 'Ũ', 'Ư', 'Ừ', 'Ứ',
			'Ự', 'Ử', 'Ữ' };
	private static final Character[] coDauY = { 'ỳ', 'ý', 'ỵ', 'ỷ', 'ỹ', 'Ỳ',
			'Ý', 'Ỵ', 'Ỷ', 'Ỹ' };
	private static final Character[] coDauD = { 'đ', 'Đ' };

	/**
	 * convert utf8 string to ascii string
	 * 
	 * @author Daniel
	 * @param text
	 * @return
	 */
	public static String utf8ToAscii(String text) {
		for (int i = 0; i < coDauA.length; i++) {
			text = text.replace(coDauA[i], 'a');
		}
		for (int i = 0; i < coDauD.length; i++) {
			text = text.replace(coDauD[i], 'd');
		}
		for (int i = 0; i < coDauE.length; i++) {
			text = text.replace(coDauE[i], 'e');
		}
		for (int i = 0; i < coDauI.length; i++) {
			text = text.replace(coDauI[i], 'i');
		}
		for (int i = 0; i < coDauO.length; i++) {
			text = text.replace(coDauO[i], 'o');
		}
		for (int i = 0; i < coDauU.length; i++) {
			text = text.replace(coDauU[i], 'u');
		}
		for (int i = 0; i < coDauY.length; i++) {
			text = text.replace(coDauY[i], 'y');
		}
		return text;
	}
}