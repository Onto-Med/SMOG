package de.imise.excel_api.model_generator;

import junit.framework.AssertionFailedError;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.assertTrue;


public class ModelGeneratorTest {

	private static final String INPUT    = "test_workbook.xlsx";
	private static final String EXPECTED = "expected";
	private static final String ACTUAL   = "actual";

	@BeforeClass
	public static void setUp() throws Exception {
		File actual = getActualFile();

		FileUtils.deleteDirectory(actual);
		Files.createDirectory(actual.toPath());

		assertTrue(actual.isDirectory());
	}

	@AfterClass
	public static void cleanUp() throws Exception {
		FileUtils.deleteDirectory(getActualFile());
	}

	@Test
	public void testGeneratedJava() throws Exception {
		ModelGenerator gen = new ModelGenerator(
			new File(Objects.requireNonNull(ModelGenerator.class.getClassLoader().getResource(INPUT)).toURI()));

		File expected = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(EXPECTED)).getPath());
		File actual   = getActualFile();

		gen.generate(actual, "test.model");

		assertTrue(dirsAreEqual(expected.toPath(), actual.toPath()));
	}

	private static File getActualFile() {
		return new File(Objects.requireNonNull(
			ModelGeneratorTest.class.getClassLoader().getResource(".")
		).getPath() + "/" + ACTUAL);
	}

	private static boolean dirsAreEqual(Path one, Path other) {
		SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				FileVisitResult result = super.visitFile(file, attrs);

				Path relativize  = one.relativize(file);
				Path fileInOther = other.resolve(relativize);

				byte[] otherBytes = Files.readAllBytes(fileInOther);
				byte[] thisBytes  = Files.readAllBytes(file);

				if (!Arrays.equals(otherBytes, thisBytes)) {
					throw new AssertionFailedError(file + " is not equal to " + fileInOther);
				}
				return result;
			}
		};

		try {
			Files.walkFileTree(one, fileVisitor);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
