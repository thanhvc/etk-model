/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.exoplatform.social.core.storage.cache.model.key;

/**
 * Immutable activity count key.
 * This key is used to cache the activity count.
 * 
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 * @version $Revision$
 */
public class ActivityCountKey extends MultitenantCacheKey {

  private IdentityKey key;

  private String baseId;

  private ActivityType type;

  public ActivityCountKey(final IdentityKey key, final ActivityType type) {
    this.key = key;
    this.type = type;
  }

  public ActivityCountKey(final IdentityKey key, final String baseId, final ActivityType type) {
    this.key = key;
    this.baseId = baseId;
    this.type = type;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ActivityCountKey)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    ActivityCountKey that = (ActivityCountKey) o;

    if (baseId != null ? !baseId.equals(that.baseId) : that.baseId != null) {
      return false;
    }
    if (key != null ? !key.equals(that.key) : that.key != null) {
      return false;
    }
    if (type != that.type) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (key != null ? key.hashCode() : 0);
    result = 31 * result + (baseId != null ? baseId.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

}