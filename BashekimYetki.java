package Helper;



public class BashekimYetki extends UserYetkiBilgilendirme {

	@Override
	public String loginMsg() {
		String diyalog=" sisteme başhekim olarak giriş yaptığınız kaydedildi kliniğe doktor atama, silme ve sisteme doktor kaydetme, silme yetkiniz bulunmaktadır.  ";
		return diyalog;
	}

}
