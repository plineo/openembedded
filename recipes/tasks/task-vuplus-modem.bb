DESCRIPTION = "Vuplus: Modem task for the Vuplus distribution"
SECTION = "vuplus/base"
LICENSE = "MIT"
PR = "r0"

inherit task

#
# task-vuplus-modem
#
DESCRIPTION_${PN} = "Vuplus: Modem support"
DEPENDS_${PN} = "enigma2-plugins"
RDEPENDS_${PN} = "\
  dreambox-modem-ppp-scripts \
  enigma2-plugin-extensions-modem \
  kernel-module-ppp-async \
  kernel-module-ppp-deflate \
  kernel-module-ppp-generic \
  ppp \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
