/*
 * Copyright 2021 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.spotless.extra.reviewdog;

import com.diffplug.spotless.extra.reviewdog.DiagnosticResult.Source;

import com.squareup.moshi.JsonAdapter;

import com.squareup.moshi.Moshi;

import com.squareup.moshi.Moshi.Builder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReviewDogTest {

	@Test
	public void blah() {
		final ReviewDog reviewDog = new ReviewDog();
		final Moshi moshi = new Builder().build();
		final JsonAdapter<DiagnosticResult> adapter = moshi.adapter(DiagnosticResult.class).indent("  ");

		final DiagnosticResult diagnostics = new DiagnosticResult();
		diagnostics.add(new DiagnosticResult.Diagnostic());
		System.out.println(adapter.toJson(diagnostics));
	}
}

class ReviewDog {
	private final Moshi moshi = new Builder().build();

	private final JsonAdapter<DiagnosticResult> adapter = moshi.adapter(DiagnosticResult.class);
}

class DiagnosticResult {

	static class Source {
		/** The source of diagnostics, e.g. {@code typescript} or {@code super lint}. Optional. */
		private String name = "Spotless";
		/** A URL to open with more information about this rule code. Optional. */
		private String url = "https://github.com/diffplug/spotless";
	}

	static class Position {
		/** Line number, starting at 1. Optional. */
		private Integer line;
		/** Column number, starting at 1 (byte count in UTF-8). Optional. */
		private Integer column;
	}

	static class Range {
		/** Required. */
		private Position start;
		/** End can be omitted. Then the range is handled as zero-length (start == end). Optional. */
		private Position end;
	}

	static class Location {
		/** File path. It could be either absolute path or relative path. */
		private String path;
		/** Range in the file path. Optional. */
		private Range range;
	}

	static class Code {
		/** This rule's code/identifier. */
		private String value;
		/** A URL to open with more information about this rule code. Optional. */
		private String url;
	}

	/** Suggestion represents a suggested text manipulation to resolve a diagnostic problem. */
	static class Suggestion {
		/** Range at which this suggestion applies. To insert text into a document create a range where start == end. */
		private Range range;
		/** A suggested text which replace the range.\n For delete operations use an empty string. */
		private String text;
	}

	static class Diagnostic {
		/** The diagnostic's message. */
		private String message;
		/** Location at which this diagnostic message applies. */
		private Location location;
		/** This diagnostic's severity. Optional. */
		private String severity;
		/** This diagnostic's rule code. Optional. */
		private Code code;
		/** Suggested fixes to resolve this diagnostic. Optional. */
		private List<Suggestion> suggestions;

	}

	/** The source of this diagnostic, e.g. 'typescript' or 'super lint'. Optional. */
	private Source source = new Source();

	/** This diagnostics' overall severity. Optional. */
	private String severity;

	private List<Diagnostic> diagnostics;

	public void add(Diagnostic diagnostic) {
		if (diagnostics == null) {
			diagnostics = new ArrayList<>();
		}
		diagnostics.add(diagnostic);
	}

}
