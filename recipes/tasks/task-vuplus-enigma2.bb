DESCRIPTION = "Vuplus: Enigma2 Task for the Vuplus Distribution"
SECTION = "vuplus/base"
LICENSE = "MIT"
PR = "r0"

inherit task

PROVIDES = "\
  task-vuplus-ui \
  ${PACKAGES} \
"

PACKAGES = "\
  task-vuplus-enigma2 \
"

#
# task-vuplus-enigma2
#
RPROVIDES_task-vuplus-enigma2 = "task-vuplus-ui"
DESCRIPTION_task-vuplus-enigma2 = "Vuplus: Enigma2 Dependencies"
RDEPENDS_task-vuplus-enigma2 = "\
  dreambox-blindscan-utils \
  enigma2 \
  enigma2-defaultservices \
  enigma2-plugin-extensions-mediascanner \
  enigma2-plugin-systemplugins-hotplug \
  enigma2-plugin-systemplugins-networkwizard \
#  enigma2-plugin-systemplugins-softwaremanager \
  enigma2-plugin-systemplugins-videotune \
  enigma2-plugin-systemplugins-factorytest \
  enigma2-streamproxy \
  tuxbox-tuxtxt-32bpp \
"

RRECOMMENDS_task-vuplus-enigma2 = "\
  aio-grab \
  python-crypt \
  python-netserver \
  python-twisted-core \
  python-twisted-protocols \
  python-twisted-web \
  enigma2-plugin-extensions-cutlisteditor \
  enigma2-plugin-extensions-graphmultiepg \
  enigma2-plugin-extensions-mediaplayer \
  enigma2-plugin-extensions-pictureplayer \
  enigma2-plugin-extensions-webinterface \
  enigma2-plugin-systemplugins-satfinder \
  enigma2-plugin-systemplugins-positionersetup \
  enigma2-plugin-systemplugins-skinselector \
  ${@base_contains("MACHINE_FEATURES", "wifi", "task-vuplus-wlan", "", d)} \
  ${@base_contains("MACHINE_FEATURES", "modem", "task-vuplus-modem", "", d)} \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
