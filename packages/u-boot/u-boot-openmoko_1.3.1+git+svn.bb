DESCRIPTION = "U-boot bootloader w/ Neo1973 (GTA01) and Neo FreeRunner (GTA02) support"
AUTHOR = "Harald Welte <laforge@openmoko.org>, OpenMoko <openmoko-kernel@lists.openmoko.org>"
LICENSE = "GPL"
SECTION = "bootloader"
PRIORITY = "optional"

UBOOT_UPSTREAM_REV = "0ec595243dc99edcd248bbcfbfd5a1dc860bde89"
UBOOT_OPENMOKO_REV = "3817"
UBOOT_MACHINES = "gta01bv2 gta01bv3 gta01bv4 gta02v2 gta02v3 gta02v4"
LOCALVERSION = "+git${UBOOT_UPSTREAM_REV}+svn${UBOOT_OPENMOKO_REV}"
PV = "1.3.1${LOCALVERSION}"
PR = "r1"

PROVIDES = "virtual/bootloader"
S = "${WORKDIR}/git"

SRC_URI = "\
  git://www.denx.de/git/u-boot.git;protocol=git;tag=${UBOOT_UPSTREAM_REV} \
  svn://svn.openmoko.org/trunk/src/target/u-boot;module=patches;rev=${UBOOT_OPENMOKO_REV};proto=http \
  file://uboot-20070311-tools_makefile_ln_sf.patch;patch=1 \
  file://makefile-no-dirafter.patch;patch=1 \
  file://fix-data-abort-from-sd-ombug799.patch;patch=1 \
"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"
TARGET_LDFLAGS = ""

do_quilt() {
        mv ${WORKDIR}/patches ${S}/patches && cd ${S} && quilt push -av
        rm -Rf patches .pc
}

do_svnrev() {
	mv -f tools/setlocalversion tools/setlocalversion.old
	echo "echo ${LOCALVERSION}" >>tools/setlocalversion
}

do_configure_prepend() {
	find . -name "*.mk" -exec sed -i 's,-mabi=apcs-gnu,,' {} \;
	find . -name "Makefile" -exec sed -i 's,-mabi=apcs-gnu,,' {} \;
	cat ${WORKDIR}/uboot-eabi-fix-HACK.patch |patch -p1
}

do_compile () {
        chmod +x board/neo1973/gta*/split_by_variant.sh
        for mach in ${UBOOT_MACHINES}
        do
                oe_runmake ${mach}_config
                oe_runmake clean
                find board -name lowlevel_foo.bin -exec rm '{}' \;
                oe_runmake all
                oe_runmake u-boot.udfu
                if [ -f u-boot.udfu ]; then
                        mv u-boot.udfu u-boot_${mach}.bin
                else
                        mv u-boot.bin u-boot_${mach}.bin
                fi
                if [ -f board/${mach}/lowlevel_foo.bin ]; then
                        mv board/${mach}/lowlevel_foo.bin \
                            lowlevel_foo_${mach}.bin
                else
                        find board -name lowlevel_foo.bin \
                            -exec mv '{}' lowlevel_foo_${mach}.bin \;
                fi
        done
}

do_deploy () {
	install -d ${DEPLOY_DIR_IMAGE}
	for mach in ${UBOOT_MACHINES}
	do
		install -m 0644 ${S}/u-boot_${mach}.bin ${DEPLOY_DIR_IMAGE}/u-boot-${mach}-${PV}-${PR}.bin
		ln -sf ${DEPLOY_DIR_IMAGE}/u-boot-${mach}-${PV}-${PR}.bin ${DEPLOY_DIR_IMAGE}/uboot-${mach}-latest.bin
		if [ -f ${S}/lowlevel_foo_${mach}.bin ]; then
			install -m 0644 ${S}/lowlevel_foo_${mach}.bin ${DEPLOY_DIR_IMAGE}/lowlevel_foo-${mach}-${PV}-${PR}.bin
			ln -sf ${DEPLOY_DIR_IMAGE}/lowlevel_foo-${mach}-${PV}-${PR}.bin ${DEPLOY_DIR_IMAGE}/lowlevel-foo-${mach}-latest.bin
		fi
	done
	install -m 0755 tools/mkimage ${STAGING_BINDIR_NATIVE}/uboot-mkimage
}

do_deploy[dirs] = "${S}"
addtask deploy before do_package after do_install
addtask quilt before do_patch after do_unpack
addtask svnrev before do_patch after do_quilt