package creativeeditor.data.tag;

import java.util.UUID;

import com.mojang.authlib.GameProfile;
import creativeeditor.data.Data;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;

public class TagGameProfile implements Data<GameProfile, CompoundNBT> {
	private final @Getter GameProfile profile;

	public TagGameProfile(String username) {
		this(new GameProfile((UUID) null, username));
	}

	public TagGameProfile(CompoundNBT nbt) {
		this(NBTUtil.readGameProfile(nbt));
	}

	public TagGameProfile(GameProfile profile) {
		this.profile = profile;
	}

	@Override
	public boolean isDefault() {
		return profile == null;
	}

	@Override
	public CompoundNBT getNBT() {
		return NBTUtil.writeGameProfile(new CompoundNBT(), profile);
	}
}
