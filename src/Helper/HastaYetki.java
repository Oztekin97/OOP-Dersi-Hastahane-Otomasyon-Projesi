package Helper;



public class HastaYetki extends UserYetkiBilgilendirme {

	@Override
	public String loginMsg() {
		String diyalog=" sisteme hasta olarak giriş yaptığınız kaydedildi sadece istediğiniz hekimden randevu alma ve iptal etme yetkiniz bulunmaktadır. ";
		return diyalog;
	}

}
