package Helper;


import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {
	public static void OptionPaneChangeButtonText() {
		UIManager.put("OptionPane.cancelButtonText", "iptal");
		UIManager.put("OptionPane.yesButtonText", "yes");
		UIManager.put("OptionPane.noButtonText", "no");
		UIManager.put("OptionPane.okButtonText", "ok");
	}

	public static void showMsg(String str) {
		OptionPaneChangeButtonText();
		String msg;
		switch (str) {
		case "doldur":
			msg = "Lütfen tüm alanları doldurunuz.";
			break;
		case "success":
			msg = "İşlem başarıyla gerçekleşti";
			break;
		case "error":
			msg="Hatalı bir işlem gerçekleşti, Lütfen tekrar deneyiniz.";
			break;
		default:
			msg = str;
		}
		JOptionPane.showMessageDialog(null, msg, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}

	public static boolean confirm(String str) {
		String msg;
		OptionPaneChangeButtonText();
		switch (str) {
		case "sure":
			msg = "Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
			break;
		default:
			msg = str;
			break;
		}
		int sonuc = JOptionPane.showConfirmDialog(null, msg, "Dikkat!", JOptionPane.YES_NO_OPTION);
		if (sonuc == 0)
			return true;
		else
			return false;
	}
}



