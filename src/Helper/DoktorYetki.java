package Helper;



public class DoktorYetki extends UserYetkiBilgilendirme {

	@Override
	public String loginMsg() {
		String diyalog=" sisteme doktor olarak giriş yaptığınız kaydedildi çalışma saatlerini ayarlama ve eğer uygun değilseniz çalışma saati silme yetkiniz bulunmaktadır. ";
		return diyalog;
	}

}
