DESCRIPTION = "Vuplus: W-LAN Task for the Vuplus Distribution"
SECTION = "vuplus/base"
LICENSE = "MIT"
PR = "r3"

inherit task

#
# task-vuplus-wlan
#
DESCRIPTION_${PN} = "Vuplus: W-LAN Support"
DEPENDS_${PN} = "enigma2-plugins"
RDEPENDS_${PN} = "\
  enigma2-plugin-systemplugins-wirelesslan \
  wireless-tools \
  wpa-supplicant \
"

WLAN_CRYPTO_MODULES = "\
  kernel-module-aes-generic \
  kernel-module-arc4 \
  kernel-module-ecb \
  kernel-module-cryptomgr \
  kernel-module-crypto-hash \
  kernel-module-aead \
  kernel-module-pcompress \
  kernel-module-crypto-blkcipher \
  kernel-module-crypto-algapi \
"

WLAN_PCI_MODULES = "\
  kernel-module-ath5k \
"

WLAN_USB_MODULES = "\
  kernel-module-rt73usb \
  kernel-module-zd1211rw \
  rt73-firmware \
  zd1211-firmware \
"

WLAN_USB_MODULES_LEGACY = "\
  zd1211b \
  wlan-rt73 \
"

RDEPENDS_${PN}_append_bm750 = "\
  ${@base_contains('PREFERRED_VERSION_linux-bm750', '2.6.18', '${WLAN_USB_MODULES_LEGACY}', '${WLAN_CRYPTO_MODULES} ${WLAN_USB_MODULES}', d)} \
"

RDEPENDS_${PN}_append_vusolo = "\
  ${@base_contains('PREFERRED_VERSION_linux-vusolo', '2.6.18', '${WLAN_USB_MODULES_LEGACY}', '${WLAN_CRYPTO_MODULES} ${WLAN_USB_MODULES}', d)} \
"


PACKAGE_ARCH = "${MACHINE_ARCH}"

