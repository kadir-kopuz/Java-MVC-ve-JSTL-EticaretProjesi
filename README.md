# 🛒 Java MVC & JSTL E-Ticaret Projesi Kurulum Kılavuzu

Bu proje; Java Servlets, JSP, JSTL ve MVC mimarisi kullanılarak geliştirilmiş bir e-ticaret web uygulamasıdır. Projeyi **Visual Studio Code (VS Code)** ve **XAMPP (phpMyAdmin)** kullanarak kendi bilgisayarınızda yerel olarak çalıştırmak için aşağıdaki adımları sırasıyla takip ediniz.

---

## 🛠️ 1. Gerekli Ön Koşullar ve Araçlar

Projeyi çalıştırmadan önce sisteminizde şu araçların kurulu olduğundan emin olun:
1. **Git:** Projeyi GitHub'dan klonlamak için.
2. **Java JDK:** JDK 11 veya JDK 17 sürümü (Önerilir).
3. **XAMPP:** Apache ve MySQL servisleri (phpMyAdmin paneli) için.
4. **Apache Tomcat 9.0.x:** Projede `javax.servlet` paketleri kullanıldığı için **kesinlikle Tomcat 9** kullanılmalıdır (Tomcat 10 ve üzeri sürümler `jakarta` yapısına geçtiği için uyumsuzdur).

### 🔌 Gerekli VS Code Eklentileri (Extensions)
VS Code içerisinden Java kodlarını derlemek ve Tomcat sunucusunu yönetmek için şu iki eklentiyi kurun:
* **Extension Pack for Java** (Microsoft)
* **Tomcat for Java** (Community eklentisi)

---

## 🚀 Adım Adım Kurulum ve Çalıştırma

### 📥 Adım 1: Projeyi GitHub'dan Klonlama
Terminalinizi (veya Komut Satırını) açın, projenin bilgisayarınızda inmesini istediğiniz klasöre gidin ve aşağıdaki komutla projeyi yerel bilgisayarınıza çekin:

```bash
git clone https://github.com/kadir-kopuz/Java-MVC-ve-JSTL-EticaretProjesi.git
```


### 🗄️ Adım 2: XAMPP ile Veritabanını Hazırlama (phpMyAdmin)

1-XAMPP Control Panel uygulamasını açın.

2-Apache ve MySQL servislerinin yanındaki "Start" butonlarına basarak servisleri başlatın.

3-Tarayıcınızdan http://localhost/phpmyadmin adresine gidin.

4-Sol menünün üstündeki "Yeni" (New) seçeneğine tıklayarak yeni bir veritabanı oluşturun (Örn. veritabanı adı: eticaret_db).

5-Oluşturduğunuz veritabanına tıklayın, üst menüden "İçe Aktar" (Import) sekmesine gidin.

6-"Dosya Seç" butonuna tıklayarak projenin ana dizininde yer alan veritabanıkodlari.sql dosyasını seçin.

7-Sayfanın en altındaki "Git" (Import) butonuna basarak tüm tabloların phpMyAdmin'e yüklenmesini sağlayın.


### 🔑 Adım 3: Veritabanı Bağlantı Ayarlarını Düzenleme
1-VS Code'u açın ve File -> Open Folder diyerek bilgisayarınıza indirdiğiniz projenin ana klasörünü seçip açın.

2-Proje klasör ağacından src/com/eticaret/util/DBConnection.java dosyasını bulun ve açın.

3-Aşağıdaki satırları kendi yerel XAMPP veritabanı ayarlarınıza göre güncelleyin:

```bash
private static final String URL = "jdbc:mysql://localhost:3306/eticaret_db";

private static final String USER = "root"; 

private static final String PASSWORD = "";
```


### 📚 Adım 4: Harici Kütüphanelerin Tanıtılması (Referenced Libraries)

Bu proje harici bir paket yöneticisi (Maven/Gradle) yerine geleneksel JAR yapısını kullanmaktadır. Projenin çalışması için gerekli olan tüm bağımlılıklar (MySQL Sürücüsü, JSTL vb.) webapp/WEB-INF/lib/ klasöründe yer almaktadır.

VS Code'un bu kütüphaneleri sınıf yoluna (Classpath) eklemesi için:

1-VS Code sol alt menüsünde yer alan "JAVA PROJECTS" panelini genişletin.

2-Projenizin altındaki Referenced Libraries yazısının yanındaki + (artı) ikonuna tıklayın.

3-Açılan dosya gezgininde projenin içindeki webapp/WEB-INF/lib/ klasörüne gidin.

4-Klasör içerisindeki tüm .jar dosyalarını seçerek projeye dahil edin.


### ⚙️ Adım 5: Tomcat Sunucusunu Ekleme ve Projeyi Dağıtma (Deploy)

1-VS Code sol menüsündeki "TOMCAT SERVERS" paneline gelin.

2-Paneldeki + ikonuna tıklayın ve bilgisayarınıza indirdiğiniz Apache Tomcat 9.0.x ana klasörünü seçerek sunucuyu VS Code'a tanıtın.

3-Eklenen Tomcat 9 sunucusunun üzerine sağ tıklayın ve "Add Web App (.war or folder)" seçeneğini seçin.

4-Açılan pencerede projenizin içerisindeki webapp klasörünü seçip onaylayın.

5-Tomcat sunucusuna tekrar sağ tıklayıp "Start" butonuna basın. Terminal ekranında sunucunun başarıyla başladığı logları göreceksiniz.

### 🌐 6. Uygulamaya Erişim
Her şey sorunsuz tamamlandıysa tarayıcınızı açıp aşağıdaki adres üzerinden uygulamayı yerel olarak test edebilirsiniz:

http://localhost:8080/webapp/home
